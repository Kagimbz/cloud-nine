package com.kagimbz.cloud_nine.auth.configs;

import com.kagimbz.cloud_nine.auth.users.UserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import reactor.core.publisher.Mono;

@Configuration
@RequiredArgsConstructor
public class UserConfig {
    private final UserRepo userRepo;

    @Bean
    public ReactiveUserDetailsService reactiveUserDetailsService() {
        return email -> userRepo.findByEmail(email)
                .map(user -> (UserDetails) user)
                .switchIfEmpty(Mono.error(new UsernameNotFoundException("User not found with email: "+ email)));
    }
}
