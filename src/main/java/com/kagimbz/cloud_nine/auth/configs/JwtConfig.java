package com.kagimbz.cloud_nine.auth.configs;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import reactor.core.publisher.Mono;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Base64;

@Configuration
@RequiredArgsConstructor
@Slf4j
public class JwtConfig {
    private final ReactiveUserDetailsService reactiveUserDetailsService;

    @Value("${auth.jwt.signing-key}")
    private String passwordSecret;

    @Value("${password.encoder.iteration}")
    private Integer iteration;

    @Value("${password.encoder.keyLength}")
    private Integer keyLength;


    @Bean
    public PasswordEncoder passwordEncoder() {
        return new PasswordEncoder() {

            @Override
            public String encode(CharSequence rawPassword) {
                try {
                    byte[] result = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA512")
                            .generateSecret(new PBEKeySpec(rawPassword.toString().toCharArray(), passwordSecret.getBytes(), iteration, keyLength))
                            .getEncoded();

                    log.info("Generated Password By Custom Password Encoder: {}",  Base64.getEncoder().encodeToString(result));

                    return Base64.getEncoder().encodeToString(result);

                } catch (NoSuchAlgorithmException | InvalidKeySpecException ex) {
                    throw new RuntimeException(ex);
                }
            }

            @Override
            public boolean matches(CharSequence rawPassword, String encodedPassword) {
                return encode(rawPassword).equals(encodedPassword);
            }

        };

    }

    @Bean
    public ReactiveAuthenticationManager reactiveAuthenticationManager(PasswordEncoder passwordEncoder) {
        return authentication -> {
            String username = authentication.getName();
            String password = authentication.getCredentials().toString();

            return reactiveUserDetailsService.findByUsername(username)
                    .flatMap(userDetails -> {
                        if (passwordEncoder.matches(password, userDetails.getPassword())) {
                            return Mono.just(new UsernamePasswordAuthenticationToken(userDetails, password, userDetails.getAuthorities()));
                        } else {
                            return Mono.empty();
                        }
                    });
        };

    }

}
