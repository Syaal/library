package com.miniproject.library.security;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@RequiredArgsConstructor
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {
    private final UserDetailServiceImpl userDetailsService;
    private final JwtTokenFilter jwtTokenFilter;

    private static final String LIBRARIAN = "LIBRARIAN";
    private static final String VISITOR = "VISITOR";

    @Bean
    public AuthenticationManager authManager() {
        var authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder());
        return new ProviderManager(authProvider);
    }

    @Autowired
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(AbstractHttpConfigurer::disable)
                .cors(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/users/login", "/users/register/{role}", "/users/all", "/category/all", "/book/all", "/book/{id}", "/penalty/all", "penalty/{id}").permitAll()
                        .requestMatchers(Auth_Swagger).permitAll()
                        .requestMatchers(HttpMethod.POST, "/category", "/book", "/penalty/create").hasRole(LIBRARIAN)
                        .requestMatchers(HttpMethod.PUT, "/users/edit-{id}", "/librarian/edit-{id}", "/anggota/edit-{id}", "/category/edit-{id}", "/book/edit-{id}").hasRole(LIBRARIAN)
                        .requestMatchers(HttpMethod.GET, "/users/{id}", "/librarian/{id}", "/librarian/all", "/category/{id}", "/book-report").hasRole(LIBRARIAN)
                        .requestMatchers(HttpMethod.DELETE, "/users/delete-{id}", "/librarian/delete-{id}", "/anggota/delete-{id}", "/penalty/{id}").hasRole(LIBRARIAN)
                        .requestMatchers(HttpMethod.POST, "/loan/borrow", "/loan/return").hasRole(VISITOR)
                        .requestMatchers(HttpMethod.GET, "/anggota/{id}", "/anggota/all").hasRole(VISITOR)
                        .anyRequest().authenticated()
                )
                .addFilterBefore(jwtTokenFilter, UsernamePasswordAuthenticationFilter.class)
                .sessionManagement(s -> s.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .build();
    }

    private static final String[] Auth_Swagger = {
            "/v3/api-docs/**",
            "/v3/api-docs.yaml",
            "/swagger-ui.html",
            "/swagger-ui/**"
    };
}