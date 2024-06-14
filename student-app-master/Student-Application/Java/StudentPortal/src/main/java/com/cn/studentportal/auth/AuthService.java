package com.cn.studentportal.auth;

import java.io.IOException;

import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.cn.studentportal.bean.UserBean;
import com.cn.studentportal.config.JwtService;
import com.cn.studentportal.entity.Role;
import com.cn.studentportal.entity.User;
import com.cn.studentportal.repository.UserRepository;
import com.cn.studentportal.util.CommonConstants;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthService {

	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;
	private final JwtService jwtService;
	private final AuthenticationManager authenticationManager;

	public AuthResponse register(RegisterRequest registerrequest) {
		
		User user = User.builder().name(registerrequest.getUserName()).userEmail(registerrequest.getUserEmail())
				.userContact(registerrequest.getUserContact())
				.userPassword(passwordEncoder.encode(registerrequest.getUserPassword()))
				.cmnStatusId(CommonConstants.Active).isAdmin(registerrequest.getIsAdmin()).build();
		
		if (registerrequest.getRole().equalsIgnoreCase("ADMIN")) {
			user.setRole(Role.ADMIN);
		} else {
			user.setRole(Role.USER);
		}

		User dbUser = userRepository.save(user);
		UserBean userBean = UserBean.builder().userId(dbUser.getUserId()).name(dbUser.getName())
				.userEmail(dbUser.getUserEmail()).userContact(dbUser.getUserContact())
				.cmnStatusId(dbUser.getCmnStatusId()).userPassword(dbUser.getUserPassword())
				.isAdmin(dbUser.getIsAdmin()).role(dbUser.getRole()).build();
		
		String accessToken = jwtService.generateToken(user);
		String refreshToken = jwtService.generateRefreshToken(user);
		return AuthResponse.builder().userBean(userBean).accessToken(accessToken).refreshToken(refreshToken).build();
	}

	public AuthResponse authenticate(AuthRequest authRequest) {
		authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(authRequest.getEmail(), 
						authRequest.getPassword()));
		User user = userRepository.findByUserEmail(authRequest.getEmail())
				.orElseThrow(() -> new UsernameNotFoundException("User Not Found...."));

		UserBean userBean = UserBean.builder().userId(user.getUserId()).name(user.getName())
				.userEmail(user.getUserEmail()).userContact(user.getUserContact()).cmnStatusId(user.getCmnStatusId())
//                .userPassword(user.getUserPassword())
				.isAdmin(user.getIsAdmin()).role(user.getRole()).build();
		String jwtToken = jwtService.generateToken(user);
		String refreshToken = jwtService.generateRefreshToken(user);
		return AuthResponse.builder().userBean(userBean).accessToken(jwtToken).refreshToken(refreshToken).build();
	}

	public void refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException {
		final String authHeaders = request.getHeader(HttpHeaders.AUTHORIZATION);
		final String refreshToken;
		final String userEmail;

		if (authHeaders == null || !authHeaders.startsWith("Bearer ")) {
			return;
		}
		refreshToken = authHeaders.substring(7);
		userEmail = jwtService.extractUserName(refreshToken);
		if (userEmail != null) {
			UserDetails userDetails = userRepository.findByUserEmail(userEmail).orElseThrow();
			if (jwtService.isTokenValid(refreshToken, userDetails)) {
				String accessToken = jwtService.generateToken(userDetails);
				AuthResponse authResponse = AuthResponse.builder().accessToken(accessToken).refreshToken(refreshToken)
						.build();
				new ObjectMapper().writeValue(response.getOutputStream(), authResponse);
			}
		}
	}

}
