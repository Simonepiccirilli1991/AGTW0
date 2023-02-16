package com.gwt.controller;

import com.gwt.service.ValidateJwtBank;
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
	@Autowired
	ValidateJwtBank validateJwtBank;
	
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

	@PostMapping("validate/body")
	public ResponseEntity<String> validate(@RequestBody UserDetails request,
			@RequestHeader("Authorization") String authorizationHeader){

		return jwtService.validate(authorizationHeader, request);
	}
	
	@PostMapping("validate")
	public ResponseEntity<String> validate(@RequestHeader("Authorization") String authorizationHeader){

		return jwtService.validateToken(authorizationHeader);
	}

	@PostMapping("validate/bank")
	public ResponseEntity<Boolean> validateBank(@RequestHeader("Authorization") String authorizationHeader){

		return new ResponseEntity<>(validateJwtBank.validateJwtBank(authorizationHeader),HttpStatus.OK);
	}
}
