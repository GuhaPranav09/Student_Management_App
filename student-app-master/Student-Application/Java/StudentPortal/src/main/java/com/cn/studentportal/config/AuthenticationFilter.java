package com.cn.studentportal.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.cn.studentportal.controller.StreamController;

import java.io.IOException;

@Slf4j
@Component
@RequiredArgsConstructor
public class AuthenticationFilter extends OncePerRequestFilter {

    private  final  JwtService jwtService;
    private final UserDetailsService userDetailsService;
    @Override
    protected void doFilterInternal(
           @NonNull HttpServletRequest request,
           @NonNull HttpServletResponse response,
           @NonNull FilterChain filterChain
    ) throws ServletException, IOException {
    	
        final String authHeaders = request.getHeader("Authorization");
        final String jwt;
        final String userEmail;
        log.info("Method entry for Authentication Filter");
        if(authHeaders == null || !authHeaders.startsWith("Bearer ")){
            filterChain.doFilter(request, response);
            return;
        }
        jwt = authHeaders.substring(7);
        userEmail = jwtService.extractUserName(jwt);
        if(userEmail != null  && SecurityContextHolder.getContext().getAuthentication() == null){
        	log.info("UserDetails method before invoke in Authentication Filter");
            UserDetails userDetails = userDetailsService.loadUserByUsername(userEmail);
            if(jwtService.isTokenValid(jwt, userDetails)){
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                            userDetails,  null, userDetails.getAuthorities());
                authToken.setDetails(
                        new WebAuthenticationDetailsSource().buildDetails(request)
                );
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
            log.info("UserDetails method after invoke in Authentication Filter");
        }

        log.info("DoFilter before invoke in Authentication Filter");
        filterChain.doFilter(request, response);
        log.info("DoFilter after invoke in Authentication Filter");
        log.info("Method Exit for Authentication Filter");
    }
}
