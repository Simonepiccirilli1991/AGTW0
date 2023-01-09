package com.gwt.service;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import com.gwt.dto.UserDetails;
import com.gwt.util.JwtUtil;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;

@Service
public class JwtService {

	@Autowired
	JwtUtil jwtUtil;


	public ResponseEntity<Boolean> createJwt(UserDetails request){

		HttpHeaders header = new HttpHeaders();

		String authString = jwtUtil.generateToken(request);

		if(ObjectUtils.isEmpty(authString))
			return new ResponseEntity<>(false,HttpStatus.SERVICE_UNAVAILABLE);

		header.add("Authorization", authString);

		return new ResponseEntity<>(true, header, HttpStatus.OK);
	}


	public ResponseEntity<String> getUsernameFromToken(String token){

		String username = jwtUtil.getUsernameFromToken(token);

		return new ResponseEntity<>(username,HttpStatus.OK);
	}

	public ResponseEntity<String> validate(String token, UserDetails request){

		if (jwtUtil.validateToken(token, request)) {

			return new ResponseEntity<>("Action performed successfully!", HttpStatus.OK);

		} else {

			return new ResponseEntity<>("Invalid JWT!", HttpStatus.UNAUTHORIZED);
		}
	}

	// validate without userDetail on request
	public ResponseEntity<String> validateToken(String token) {
		try {
			Claims claims = jwtUtil.getAllClaimsFromToken(token);
			Date expirationDate = claims.getExpiration();
			Date now = new Date();
			if(!expirationDate.before(now))
				return new ResponseEntity<>("Action performed successfully!",HttpStatus.OK);
			else
				return new ResponseEntity<>("Invalid JWT!", HttpStatus.UNAUTHORIZED);
			
		} catch (JwtException | IllegalArgumentException e) {
			return new ResponseEntity<>("Error on jwt validation" , HttpStatus.UNAUTHORIZED);
		}
	}
}
