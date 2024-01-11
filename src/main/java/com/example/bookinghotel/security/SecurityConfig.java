package com.example.bookinghotel.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    private final CustomUserDetailService userDetailService;
    private final PasswordEncoder passwordEncoder;

    private final CustomFilter customFilter;

    public SecurityConfig(CustomUserDetailService userDetailService, PasswordEncoder passwordEncoder, CustomFilter customFilter) {
        this.userDetailService = userDetailService;
        this.passwordEncoder = passwordEncoder;
        this.customFilter = customFilter;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setPasswordEncoder(passwordEncoder);
        provider.setUserDetailsService(userDetailService);
        return provider;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf(csrf->csrf.disable());
        http.authorizeHttpRequests(authorize -> authorize
                .requestMatchers("/admin/blogs/own-blog").hasRole("ADMIN")
                .requestMatchers(
                        "/admin/blogs/**", "/api/v1/admin/blogs/**").hasAnyRole( "ADMIN")
                .requestMatchers("/admin/users/**").hasRole("ADMIN")
                .requestMatchers("/home","/property","property-detail","/blogs","/blog-detail").hasAnyRole("USER","ADMIN")
                .anyRequest().permitAll()
        );
        http.logout(logout -> logout
                .logoutSuccessUrl("/home")
                .deleteCookies("JNADJFN")
                .invalidateHttpSession(true)
                .permitAll()
        );
        http.authenticationProvider(authenticationProvider());
        http.addFilterBefore(customFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }
}
