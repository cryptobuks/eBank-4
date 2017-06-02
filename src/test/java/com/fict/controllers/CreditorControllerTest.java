package com.fict.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fict.entities.Creditor;
import com.fict.services.CreditorService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Created by stevo on 6/2/17.
 */

@RunWith(SpringRunner.class)
@WebMvcTest(CreditorController.class)
public class CreditorControllerTest {

	@Autowired
	private WebApplicationContext webApplicationContext;

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private CreditorService creditorService;

	@Before
	public void setUp() {
		mockMvc = MockMvcBuilders
				.webAppContextSetup(webApplicationContext)
				.apply(springSecurity())
				.build();
	}

	@Test
	@WithMockUser
	public void getCreditorShouldReturnCreditor() throws Exception {

		Creditor shouldReturn = getDummyCreditor();
		Mockito.when(creditorService.findCreditorById(any(Long.class))).thenReturn(shouldReturn);

		mockMvc.perform(get("/admin/creditor/{id}", 1L))
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
				.andExpect(content().json(new ObjectMapper().writeValueAsString(shouldReturn)))
				.andDo(print())
				.andReturn();

	}

	@Test
	@WithMockUser(authorities = "NORMAL")
	public void getCreditorWhenNormalShouldReturnForbidden() throws Exception {

		mockMvc.perform(get("/admin/creditor/{id}", 1L))
				.andExpect(status().isForbidden())
				.andDo(print())
				.andReturn();

	}

	@Test
	@WithAnonymousUser
	public void getCreditorWhenUnauthenticatedShouldReturn401() throws Exception {

		mockMvc.perform(get("/admin/creditor/{id}", 1L))
				.andExpect(status().isUnauthorized())
				.andDo(print())
				.andReturn();

	}

	private Creditor getDummyCreditor() {
		Creditor creditor = new Creditor();
		creditor.setId(1L);
		creditor.setName("TestCreditor");
		creditor.setAddress("Test adresa");
		creditor.setImeNaBanka("test banka");
		creditor.setTransactionNumber("1234123412341234");
		return creditor;
	}

}
