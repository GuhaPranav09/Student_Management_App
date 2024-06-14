package com.cn.studentportal.auth;

import java.io.IOException;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@CrossOrigin("*") 
public class AuthenticationController {
    
	private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(
            @RequestBody RegisterRequest registerrequest
    ){
    	log.info("Method register() for Authentication Controller");
        return ResponseEntity.ok(authService.register(registerrequest));
    }
    @PostMapping("/authenticate")
    public ResponseEntity<AuthResponse> authenticate(		
            @RequestBody AuthRequest authRequest
    ){
    	log.info("Method authenticate() entry for Authentication Controller");
        AuthResponse authenticate = authService.authenticate(authRequest);
        log.info("Method authenticate() exit for Authentication Controller");
//         System.out.println(authenticate);
        return ResponseEntity.ok(authenticate);
    }

    @PostMapping("/refresh-token")
    public void refreshToken(
           HttpServletRequest request, HttpServletResponse response
    ) throws IOException {
    	log.info("Method refreshToken() for Authentication Controller");
        authService.refreshToken(request, response);
    }

}
