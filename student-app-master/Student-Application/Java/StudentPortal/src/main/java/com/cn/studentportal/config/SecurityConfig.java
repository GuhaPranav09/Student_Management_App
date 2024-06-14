package com.cn.studentportal.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.cn.studentportal.controller.StreamController;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final AuthenticationProvider authenticationProvider;
    private final AuthenticationFilter authenticationFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    	
    	log.info("Method securityFilterChain() entry for SecurityConfig");

        http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(req ->
                                req                                
                                .requestMatchers("/auth/**").permitAll()
                                .requestMatchers(
                                		"/user/generateotp/**",
                                		"/user/verify/**",
                                		"/user/updatepassword/**",
                                		"/auth/authenticate",
                                		"/user/save",                                		
                                		"/user/**" 
                                		)
                                .permitAll()
//                                        .requestMatchers("/student/**").hasAuthority("ADMIN")
                                        .anyRequest().authenticated()
                )
                .sessionManagement( session ->session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(authenticationFilter, UsernamePasswordAuthenticationFilter.class);
        log.info("Method securityFilterChain() exit for SecurityConfig");
        return http.build();
    }
}
