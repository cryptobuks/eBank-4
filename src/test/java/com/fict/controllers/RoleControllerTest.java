package com.fict.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fict.entities.Role;
import com.fict.services.RoleService;
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
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(RoleController.class)
public class RoleControllerTest {

	@Autowired
	private WebApplicationContext webApplicationContext;

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private RoleService roleService;

	@Before
	public void setUp() {
		mockMvc = MockMvcBuilders
				.webAppContextSetup(webApplicationContext)
				.apply(springSecurity())
				.build();
	}

	@Test
	@WithMockUser
	public void getRoleShouldReturnRole() throws Exception {

		Role shouldReturn = getDummyRole();

		Mockito.when(roleService.findRoleById(any(Long.class))).thenReturn(shouldReturn);

		mockMvc.perform(get("/admin/role/{id}", 1L))
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
				.andExpect(content().json(new ObjectMapper().writeValueAsString(shouldReturn)))
				.andDo(print())
				.andReturn();

	}

	@Test
	@WithMockUser(authorities = "NORMAL")
	public void getRoleWhenNormalShouldReturnForbidden() throws Exception {

		mockMvc.perform(get("/admin/role/{id}", 1L))
				.andExpect(status().isForbidden())
				.andReturn();

	}

	@Test
	@WithAnonymousUser
	public void getRoleWhenNotAuthenticatedShouldReturn401() throws Exception {

		mockMvc.perform(get("/admin/role/{id}", 1L))
				.andExpect(status().isUnauthorized())
				.andReturn();

	}

	private Role getDummyRole() {
		Role role = new Role();
		role.setId(1L);
		role.setName("ADMIN");
		return role;
	}

}
