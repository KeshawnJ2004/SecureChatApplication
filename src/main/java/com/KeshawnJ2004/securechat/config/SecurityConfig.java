package com.KeshawnJ2004.securechat.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.config.http.SessionCreationPolicy;

@Configuration
public class SecurityConfig {

    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                // Disable CSRF for simplicity (we’re building an API)
                .csrf(csrf -> csrf.disable())

                // Stateless session (no server‑side session)
                .sessionManagement(sm -> sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS))

                // Authorize requests
                .authorizeHttpRequests(auth -> auth
                        // Allow anyone to call /auth/** (register, login)
                        .requestMatchers("/auth/**").permitAll()
                        // Any other endpoint must be authenticated
                        .anyRequest().authenticated()
                )

                // No default login form — just reject with 401
                .httpBasic(Customizer.withDefaults());

        return http.build();
    }
}
