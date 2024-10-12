package com.kagimbz.cloud_nine.auth.users;

import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface UserRepo extends R2dbcRepository<User, Long> {
    Mono<User> findByEmail(String email);

    @Query("SELECT is_logged_in FROM users WHERE email = :email")
    Mono<Boolean> isLoggedIn(String email);

    Mono<Boolean> existsByEmail(String email);
}

