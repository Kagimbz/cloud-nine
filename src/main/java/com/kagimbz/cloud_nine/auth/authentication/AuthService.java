package com.kagimbz.cloud_nine.auth.authentication;

import com.kagimbz.cloud_nine.auth.authentication.dto.AuthResponse;
import com.kagimbz.cloud_nine.auth.authentication.dto.LoginRequest;
import com.kagimbz.cloud_nine.auth.authentication.dto.SignupRequest;
import com.kagimbz.cloud_nine.auth.authentication.dto.UserInfo;
import com.kagimbz.cloud_nine.auth.jwt.JwtService;
import com.kagimbz.cloud_nine.auth.role.Role;
import com.kagimbz.cloud_nine.auth.users.User;
import com.kagimbz.cloud_nine.auth.users.UserRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthService {
    private final UserRepo userRepo;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final ReactiveAuthenticationManager reactiveAuthenticationManager;

    public Mono<AuthResponse> createAccount(Mono<SignupRequest> request) {
        return request
                .flatMap(signupRequest -> userRepo.existsByEmail(signupRequest.getEmail())
                        .flatMap(exists -> {
                            if (exists) {
                                return Mono.just(AuthResponse.builder()
                                        .statusCode(HttpStatus.NOT_ACCEPTABLE.value())
                                        .message("User Already Exists!")
                                        .build());
                            } else {
                                User user = new User();

                                user.setFirstName(signupRequest.getFirstName());
                                user.setLastName(signupRequest.getLastName());
                                user.setEmail(signupRequest.getEmail());
                                user.setPassword(passwordEncoder.encode(signupRequest.getPassword()));
                                user.setLoggedIn(true);
                                user.setRole(Role.CUSTOMER);

                                return userRepo.save(user).flatMap(newUser -> {
                                    String jwt = jwtService.generateToken(newUser);

                                    UserInfo userInfo = UserInfo.builder()
                                            .firstName(newUser.getFirstName())
                                            .lastName(newUser.getLastName())
                                            .email(newUser.getEmail())
                                            .role(newUser.getRole())
                                            .build();

                                    return Mono.just(AuthResponse.builder()
                                            .jwt(jwt)
                                            .userInfo(userInfo)
                                            .statusCode(HttpStatus.CREATED.value())
                                            .message("Account Created Successfully")
                                            .build());
                                });

                            }

                        })

                );

    }


    public Mono<AuthResponse> login(Mono<LoginRequest> request) {
        return request.flatMap(loginRequest -> userRepo.findByEmail(loginRequest.getEmail())
                .flatMap(user -> {
                    if (!user.isActive()) {
                        log.error("Account Inactive!");
                        return Mono.just(AuthResponse.builder()
                                .statusCode(HttpStatus.FORBIDDEN.value())
                                .message("Account Inactive! Contact System Admin.")
                                .build());
                    }

                    if (user.isLoggedIn()) {
                        return Mono.just(AuthResponse.builder()
                                .statusCode(HttpStatus.FORBIDDEN.value())
                                .message("User cannot have more than one session!")
                                .build());
                    }

                    return reactiveAuthenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()))
                            .flatMap(auth -> {

                                String jwt = jwtService.generateToken(user);

                                user.setLoggedIn(true);

                                return Mono.deferContextual(Mono::just)
                                        .flatMap(context -> {
                                            ServerWebExchange exchange = context.get(ServerWebExchange.class);

                                            return exchange.getSession()
                                                    .flatMap(webSession -> {
                                                        SecurityContext securityContext = SecurityContextHolder.createEmptyContext();
                                                        securityContext.setAuthentication(auth);

                                                        // Save SecurityContext in session
                                                        webSession.getAttributes().put("SPRING_SECURITY_CONTEXT", securityContext);

                                                        // Save updated user
                                                        return userRepo.save(user).flatMap(updatedUser -> {
                                                            UserInfo userInfo = UserInfo.builder()
                                                                    .firstName(user.getFirstName())
                                                                    .lastName(user.getLastName())
                                                                    .email(user.getEmail())
                                                                    .role(user.getRole())
                                                                    .build();

                                                            return Mono.just(AuthResponse.builder()
                                                                    .jwt(jwt)
                                                                    .userInfo(userInfo)
                                                                    .statusCode(HttpStatus.OK.value())
                                                                    .message("Login Successful")
                                                                    .build());
                                                        });
                                                    });
                                        });

//                                return ReactiveSecurityContextHolder.getContext()
//                                        .defaultIfEmpty(SecurityContextHolder.createEmptyContext())
//                                        .flatMap(securityContext -> {
//                                            securityContext.setAuthentication(auth);
//                                            return userRepo.save(user).flatMap(updatedUser -> {
//                                                UserInfo userInfo = UserInfo.builder()
//                                                        .firstName(user.getFirstName())
//                                                        .lastName(user.getLastName())
//                                                        .email(user.getEmail())
//                                                        .role(user.getRole())
//                                                        .build();
//
//                                                return Mono.just(AuthResponse.builder()
//                                                        .jwt(jwt)
//                                                        .userInfo(userInfo)
//                                                        .statusCode(HttpStatus.OK.value())
//                                                        .message("Login Successful")
//                                                        .build());
//                                            });
//                                        });


                            })
                            .switchIfEmpty(Mono.defer(() -> {
                                log.error("Invalid credentials for user with email: {}", loginRequest.getEmail());
                                return Mono.just(AuthResponse.builder()
                                        .statusCode(HttpStatus.UNAUTHORIZED.value())
                                        .message("Invalid credentials! Please check your email and password.")
                                        .build());
                            }));


                })
                .switchIfEmpty(Mono.just(AuthResponse.builder()
                        .statusCode(HttpStatus.UNAUTHORIZED.value())
                        .message("Invalid credentials! Please check your email and password.")
                        .build())
                )
        );

    }

    public Mono<AuthResponse> logout(Mono<String> emailMono) {
        return emailMono.flatMap(email -> userRepo.findByEmail(email)
                .flatMap(user -> {
                    user.setLoggedIn(false);
                    return userRepo.save(user).flatMap(updatedUser -> Mono.just(AuthResponse.builder()
                            .statusCode(HttpStatus.OK.value())
                            .message("Logout Successful")
                            .build()));

                })
                .switchIfEmpty(Mono.just(AuthResponse.builder()
                        .statusCode(HttpStatus.UNAUTHORIZED.value())
                        .message("Invalid credentials!")
                        .build())
                )

        );

    }

    @Scheduled(cron = "0 0 0 * * ?") // Run every midnight
    public void automaticLogout() {
        userRepo.findAll()
                .flatMap(user -> {
                    user.setLoggedIn(false);
                    return userRepo.save(user);
                })
                .doOnError(error -> {
                    log.error("Error during automatic logout: {}", error.getMessage(), error);
                })
                .subscribe();

    }

}
