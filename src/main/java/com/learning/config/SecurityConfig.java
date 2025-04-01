package com.learning.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.learning.jwt.CustomAuthenticationEntryPoint;
import com.learning.jwt.CustomUserDetailsService;
import com.learning.jwt.CustomUserDetailsService2;
import com.learning.jwt.JwtAuthenticationFilter;
import com.learning.jwt.JwtSessionFilter;
import com.learning.jwt.JwtUtil;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

    @Autowired
    private JwtUtil jwtUtil;
    
    @Autowired
    private CustomUserDetailsService customUserDetailsService;
    
    @Autowired
    private CustomUserDetailsService2 customUserDetailsService2;
    
    @Autowired
    private CustomAuthenticationEntryPoint customAuthenticationEntryPoint;
    
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf(csrf -> csrf.disable())
            .authorizeHttpRequests(auth->
            auth.requestMatchers("/", "/user/register", "/user/login", "/admin/register", "/admin/login").permitAll() 
            .requestMatchers("/views/**").permitAll()
            .anyRequest().authenticated())
            .sessionManagement(session-> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .exceptionHandling(exceptionHandling -> exceptionHandling.authenticationEntryPoint(customAuthenticationEntryPoint))
            .addFilterBefore(new JwtSessionFilter(), UsernamePasswordAuthenticationFilter.class)
            .addFilterBefore(new JwtAuthenticationFilter(jwtUtil,customUserDetailsService,customUserDetailsService2), UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}
