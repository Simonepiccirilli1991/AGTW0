package com.gwt.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import com.gwt.dto.UserDetails;
import com.gwt.util.JwtUtil;

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
}
