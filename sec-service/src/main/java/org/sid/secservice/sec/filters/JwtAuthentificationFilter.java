package org.sid.secservice.sec.filters;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JwtAuthentificationFilter extends UsernamePasswordAuthenticationFilter{
	 private AuthenticationManager authenticationManager;
	 
	 public JwtAuthentificationFilter(AuthenticationManager authenticationManager) {
		this.authenticationManager=authenticationManager;
	}
	 
	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
			throws AuthenticationException {
		System.out.println("attemptAuthentication");
		
		String username=request.getParameter("username");
		String password=request.getParameter("password");
		UsernamePasswordAuthenticationToken authenticationToken= new UsernamePasswordAuthenticationToken(username, password);
		return authenticationManager.authenticate(authenticationToken);
	}
@Override
protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
		Authentication authResult) throws IOException, ServletException {
	System.out.println("successfulAuthentication");
	User user=(User) authResult.getPrincipal();
	Algorithm algorithm=Algorithm.HMAC256("mysecret1234");
	String jwtAccessToken=JWT.create()
			.withSubject(user.getUsername()).withExpiresAt(new Date(System.currentTimeMillis()+5*6*1000))
			.withIssuer(request.getRequestURL().toString())
			.withClaim("role", user.getAuthorities().stream().map(ga->ga.getAuthority()).collect(Collectors.toList())).sign(algorithm);
	response.setHeader("Authorization", jwtAccessToken);
	/////////////////////////////////////////////////////////////////////////////////////////////
	String jwtRefreshToken=JWT.create()
			.withSubject(user.getUsername()).withExpiresAt(new Date(System.currentTimeMillis()+15*6*1000))
			.withIssuer(request.getRequestURL().toString())
			.sign(algorithm);
	
	Map<String,String>idToken=new HashMap<>();
	idToken.put("access-token", jwtAccessToken);
	idToken.put("refresh-token", jwtRefreshToken);
	response.setContentType("application/json");
    new ObjectMapper().writeValue(response.getOutputStream(), idToken);
	response.setHeader("Authorization", jwtRefreshToken);
}
}
