package com.gwt.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import com.gwt.dto.UserDetails;
import com.gwt.service.JwtService;

@RestController
public class GtwController {

	@Autowired
	JwtService jwtService;
	
	
	@PostMapping("create")
	public ResponseEntity<Boolean> create(@RequestBody UserDetails request){

		return jwtService.createJwt(request);
	}


	@GetMapping("get/username")
	public ResponseEntity<String> getUsername(@RequestHeader("Authorization") String authorizationHeader){

		if(ObjectUtils.isEmpty(authorizationHeader))
			return new ResponseEntity<>("",HttpStatus.BAD_REQUEST);

		return jwtService.getUsernameFromToken(authorizationHeader);
	}

	@PostMapping("validate")
	public ResponseEntity<String> validate(@RequestBody UserDetails request,
			@RequestHeader("Authorization") String authorizationHeader){

		return jwtService.validate(authorizationHeader, request);
	}
	
	@PostMapping("validate/2")
	public ResponseEntity<String> validate(@RequestHeader("Authorization") String authorizationHeader){

		return jwtService.validateToken(authorizationHeader);
	}
}
