package com.kagimbz.cloud_nine.auth.authentication;

import com.kagimbz.cloud_nine.auth.authentication.dto.AuthResponse;
import com.kagimbz.cloud_nine.auth.authentication.dto.LoginRequest;
import com.kagimbz.cloud_nine.auth.authentication.dto.SignupRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping(path = "/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping(path = "/create-account")
    public ResponseEntity<Mono<AuthResponse>> createAccount(@RequestBody Mono<SignupRequest> request) {
        return ResponseEntity.ok(authService.createAccount(request));
    }

    @PutMapping(path = "/login")
    public ResponseEntity<Mono<AuthResponse>> login(@RequestBody Mono<LoginRequest> request) {
        return ResponseEntity.ok(authService.login(request));
    }

    @PutMapping(path = "/logout")
    public ResponseEntity<Mono<AuthResponse>> logout() {
        Mono<String> emailMono = ReactiveSecurityContextHolder.getContext().map(securityContext -> securityContext.getAuthentication().getName());
        return ResponseEntity.ok(authService.logout(emailMono));
    }

}
