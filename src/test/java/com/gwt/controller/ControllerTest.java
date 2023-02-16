package com.gwt.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gwt.dto.UserDetails;

@SpringBootTest
@AutoConfigureMockMvc
public class ControllerTest {

	@Autowired
	MockMvc mvc;
	
	ObjectMapper mapper = new ObjectMapper();


	@Test
	public void createJwtOK() throws Exception {

		UserDetails request = new UserDetails();

		request.setPsw("pswProva");
		request.setUsername("usrnmProva");

		MockHttpServletResponse iResp = mvc.perform(post("/create")
				.contentType("application/json")
				.content(mapper.writeValueAsString(request)))
				.andExpect(status().isOk()).andReturn().getResponse();


		mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

		assertThat(iResp.getContentAsString()).isEqualTo("true");
		assertThat(iResp.getHeader("Authorization")).isNotNull();

	}

	@Test
	public void createAndValidateOK() throws Exception {

		UserDetails request = new UserDetails();

		request.setPsw("pswProva");
		request.setUsername("usrnmProva");

		MockHttpServletResponse createResp = mvc.perform(post("/create")
				.contentType("application/json")
				.content(mapper.writeValueAsString(request)))
				.andExpect(status().isOk()).andReturn().getResponse();

		assertThat(createResp.getContentAsString()).isEqualTo("true");
		assertThat(createResp.getHeader("Authorization")).isNotNull();

		String validateResp = mvc.perform(post("/validate/body")
				.contentType("application/json")
				.header("Authorization", createResp.getHeader("Authorization"))
				.content(mapper.writeValueAsString(request)))
				.andExpect(status().isOk()).andReturn().getResponse().getContentAsString();


		assertThat(validateResp).isEqualTo("Action performed successfully!");
	}
	
	// validate with no body
	@Test
	public void createAndValidateNoBodyOK() throws Exception {
		
		UserDetails request = new UserDetails();

		request.setPsw("pswProva");
		request.setUsername("usrnmProva");

		MockHttpServletResponse createResp = mvc.perform(post("/create")
				.contentType("application/json")
				.content(mapper.writeValueAsString(request)))
				.andExpect(status().isOk()).andReturn().getResponse();

		assertThat(createResp.getContentAsString()).isEqualTo("true");
		assertThat(createResp.getHeader("Authorization")).isNotNull();

		String validateResp = mvc.perform(post("/validate")
				.contentType("application/json")
				.header("Authorization", createResp.getHeader("Authorization")))
				.andExpect(status().isOk()).andReturn().getResponse().getContentAsString();


		assertThat(validateResp).isEqualTo("Action performed successfully!");
	}
	
	@Test
	public void createAndGetUsernameOK() throws Exception {
		UserDetails request = new UserDetails();

		request.setPsw("pswProva");
		request.setUsername("usrnmProva");

		MockHttpServletResponse createResp = mvc.perform(post("/create")
				.contentType("application/json")
				.content(mapper.writeValueAsString(request)))
				.andExpect(status().isOk()).andReturn().getResponse();

		assertThat(createResp.getContentAsString()).isEqualTo("true");
		assertThat(createResp.getHeader("Authorization")).isNotNull();

		String validateResp = mvc.perform(get("/get/username")
				.contentType("application/json")
				.header("Authorization", createResp.getHeader("Authorization")))
				.andExpect(status().isOk()).andReturn().getResponse().getContentAsString();


		assertThat(validateResp).isEqualTo("usrnmProva");
	}
	
	@Test
	public void createAndValidateTestKO() throws Exception {
		
		UserDetails request = new UserDetails();

		request.setPsw("pswProva");
		request.setUsername("usrnmProva");

		MockHttpServletResponse createResp = mvc.perform(post("/create")
				.contentType("application/json")
				.content(mapper.writeValueAsString(request)))
				.andExpect(status().isOk()).andReturn().getResponse();

		assertThat(createResp.getContentAsString()).isEqualTo("true");
		assertThat(createResp.getHeader("Authorization")).isNotNull();

		String validateResp = mvc.perform(post("/validate")
				.contentType("application/json")
				.header("Authorization", "stotasso"))
				.andExpect(status().isUnauthorized()).andReturn().getResponse().getContentAsString();


		assertThat(validateResp).isEqualTo("Error on jwt validation");
	}

	@Test
	public void createAndValidateTestBankOK() throws Exception {

		UserDetails request = new UserDetails();

		request.setPsw("pswProva");
		request.setUsername("Torito");

		MockHttpServletResponse createResp = mvc.perform(post("/create")
						.contentType("application/json")
						.content(mapper.writeValueAsString(request)))
				.andExpect(status().isOk()).andReturn().getResponse();

		assertThat(createResp.getContentAsString()).isEqualTo("true");
		assertThat(createResp.getHeader("Authorization")).isNotNull();

		String validateResp = mvc.perform(post("/validate/bank")
						.contentType("application/json")
						.header("Authorization", createResp.getHeader("Authorization")))
				.andExpect(status().isOk()).andReturn().getResponse().getContentAsString();


		assertThat(validateResp).isEqualTo("Bank user admitted");
	}

	@Test
	public void createAndValidateTestBankKO() throws Exception {

		UserDetails request = new UserDetails();

		request.setPsw("pswProva");
		request.setUsername("ToninoNonVa");

		MockHttpServletResponse createResp = mvc.perform(post("/create")
						.contentType("application/json")
						.content(mapper.writeValueAsString(request)))
				.andExpect(status().isOk()).andReturn().getResponse();

		assertThat(createResp.getContentAsString()).isEqualTo("true");
		assertThat(createResp.getHeader("Authorization")).isNotNull();

		String validateResp = mvc.perform(post("/validate/bank")
						.contentType("application/json")
						.header("Authorization", createResp.getHeader("Authorization")))
				.andExpect(status().isOk()).andReturn().getResponse().getContentAsString();


		assertThat(validateResp).isEqualTo("Bank user not admitted");
	}
}
