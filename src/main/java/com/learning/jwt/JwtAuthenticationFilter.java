package com.learning.jwt;

import java.io.IOException;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@Configuration
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

	private final JwtUtil jwtUtil;
    private final CustomUserDetailsService customUserDetailsService;
    private final CustomUserDetailsService2 customUserDetailsService2;

  
    public JwtAuthenticationFilter(JwtUtil jwtUtil, CustomUserDetailsService customUserDetailsService,CustomUserDetailsService2 customUserDetailsService2) {
        this.jwtUtil = jwtUtil;
        this.customUserDetailsService = customUserDetailsService;
        this.customUserDetailsService2 = customUserDetailsService2;
    }
    
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {
    	HttpSession session = request.getSession(false);
    	
    	String requestURI = request.getRequestURI();

        if (requestURI.equals("/") || requestURI.startsWith("/public/")) {
            chain.doFilter(request, response);
            return;
        }
    	
        String header = request.getHeader("Authorization");
        String email = null;
        String jwt = null;

        try {
        if (header != null && header.startsWith("Bearer ")) {
            jwt = header.substring(7).trim();
            email = jwtUtil.extractEmail(jwt);
        }

        if (email != null && SecurityContextHolder.getContext().getAuthentication() == null) {
        	UserDetails userDetails ;
        	
        	System.out.println("loggedInAdmin"+ session.getAttribute("loggedInAdmin"));
        	
        	if(session.getAttribute("loggedInAdmin")==null) {
        		userDetails = customUserDetailsService2.loadUserByUsername(email);
        		System.out.println("userDetails"+userDetails);
        	}else {
        		userDetails = customUserDetailsService.loadUserByUsername(email);
        	}
             
            System.out.println(email + userDetails);
            if (jwtUtil.validateToken(jwt, userDetails.getUsername())) {
                UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(
                        email, null, null); 
                auth.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(auth);
            }
        }
    	
    }catch(io.jsonwebtoken.ExpiredJwtException ex){
    	System.out.println("Session expired : custom");
    	response.sendRedirect("/");
    	return;
    }catch(org.springframework.security.core.userdetails.UsernameNotFoundException u) {
    	System.out.println("user not found exception");
    	response.sendRedirect("/");
    	return;
    }
        chain.doFilter(request, response);
    }
}