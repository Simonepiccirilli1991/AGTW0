package com.gwt.service;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;

import com.gwt.dto.UserDetails;

@SpringBootTest
public class ServiceTest {

	@Autowired
	JwtService jwtService;


	@Test
	public void createToken() {


		UserDetails request = new UserDetails();

		request.setPsw("pswProva");
		request.setUsername("usrnmProva");

		ResponseEntity<Boolean> response = jwtService.createJwt(request);

		assertThat(response.getBody()).isEqualTo(true);
		assertThat(response.getHeaders().getFirst("Authorization")).isNotNull();
	}


	@Test
	public void createAndValidate() {

		UserDetails request = new UserDetails();

		request.setPsw("pswProva");
		request.setUsername("usrnmProva");

		ResponseEntity<Boolean> createResponse = jwtService.createJwt(request);

		assertThat(createResponse.getBody()).isEqualTo(true);
		assertThat(createResponse.getHeaders().getFirst("Authorization")).isNotNull();

		String token = createResponse.getHeaders().getFirst("Authorization");

		ResponseEntity<String> validateResponse = jwtService.validate(token,request);

		assertThat(validateResponse.getBody()).isEqualTo("Action performed successfully!");
	}
	// no body request on validate
	@Test
	public void createAndValidateNoBody() {

		UserDetails request = new UserDetails();

		request.setPsw("pswProva");
		request.setUsername("usrnmProva");

		ResponseEntity<Boolean> createResponse = jwtService.createJwt(request);

		assertThat(createResponse.getBody()).isEqualTo(true);
		assertThat(createResponse.getHeaders().getFirst("Authorization")).isNotNull();

		String token = createResponse.getHeaders().getFirst("Authorization");

		ResponseEntity<String> validateResponse = jwtService.validateToken(token);

		assertThat(validateResponse.getBody()).isEqualTo("Action performed successfully!");
	}

	@Test
	public void createAndGetUsername() {

		UserDetails request = new UserDetails();

		request.setPsw("pswProva");
		request.setUsername("usrnmProva");

		ResponseEntity<Boolean> createResponse = jwtService.createJwt(request);

		assertThat(createResponse.getBody()).isEqualTo(true);
		assertThat(createResponse.getHeaders().getFirst("Authorization")).isNotNull();

		String token = createResponse.getHeaders().getFirst("Authorization");

		ResponseEntity<String> validateResponse = jwtService.getUsernameFromToken(token);

		assertThat(validateResponse.getBody()).isEqualTo("usrnmProva");
	}
}
