package com.example.mydiscount.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

    private UserDetailsService userDetailsService;

    public SecurityConfiguration(UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.formLogin().loginPage("/auth/login").permitAll();
        http.authorizeRequests().antMatchers("/auth/login").permitAll();
        http.authorizeRequests().antMatchers("/auth/register").permitAll();
        http.authorizeRequests().antMatchers("/auth/logout").permitAll();
        http.authorizeRequests().antMatchers("/").permitAll();
        http.authorizeRequests().antMatchers("/ws/**").permitAll();
        http.authorizeRequests().antMatchers("/resources/**").permitAll().anyRequest().permitAll();
        http.exceptionHandling().accessDeniedPage("/403");
        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManagerBean(AuthenticationConfiguration autoConfiguration) throws Exception {
        return autoConfiguration.getAuthenticationManager();
    }
}
