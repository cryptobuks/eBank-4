package com.fict.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fict.configs.TestSecurityConfiguration;
import com.fict.entities.Customer;
import com.fict.services.CustomerService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.*;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.TestingAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configurers.GlobalAuthenticationConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.context.support.WithSecurityContextTestExecutionListener;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import javax.money.CurrencyUnit;
import javax.money.MonetaryCurrencies;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import static org.assertj.core.api.Java6Assertions.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(CustomerController.class)
public class CustomerControllerTest {


    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CustomerService customerService;

    private TestingAuthenticationToken testingAuthenticationToken;

    @Before
    public void setUp() {
        mockMvc = MockMvcBuilders
				.webAppContextSetup(webApplicationContext)
				.apply(springSecurity())
				.build();
		System.out.println(SecurityContextHolder.getContext());
	}

    @Test
	@WithMockUser("kondinskis@gmail.com")
    public void getAuthenticatedCustomerShouldReturnCustomer() throws Exception {

    	String email = SecurityContextHolder.getContext().getAuthentication().getName();

		Mockito.when(customerService.findCustomerByEmail(email)).thenReturn(getDummyCustomer());

        mockMvc.perform(get("/customer"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(content().json(new ObjectMapper().writeValueAsString(
                        customerService.findCustomerByEmail(email))))
                .andDo(print())
                .andReturn();

    }

    @Test
	@WithAnonymousUser
	public void getAuthenticatedCustomerWhenNotAuthenticatedShouldReturn401() throws Exception {

    	mockMvc.perform(get("/customer"))
				.andExpect(status().isUnauthorized())
				.andReturn();

	}

	@Test
	@WithMockUser
	public void registerCustomerShouldReturnCustomer() throws Exception {

    	Customer customer = getDummyCustomer();

    	Mockito.when(customerService.registerCustomer(any(Customer.class))).thenReturn(customer);

    	mockMvc.perform(post("/customer/register")
				.contentType(MediaType.APPLICATION_JSON)
				.content(new ObjectMapper().writeValueAsString(customer)))
				.andExpect(status().isOk())
				.andExpect(content().json(new ObjectMapper().writeValueAsString(customerService.registerCustomer(customer))))
				.andReturn();

	}

	@Test
	@WithMockUser
	public void registerCustomerWhenNoCustomerShouldReturnBadRequest() throws Exception {

    	mockMvc.perform(post("/customer/register"))
				.andExpect(status().isBadRequest())
				.andReturn();

	}

	@Test
	@WithMockUser
	public void editCustomerShouldReturnCustomer() throws Exception {

    	Customer customer = getDummyCustomer();
    	Mockito.when(customerService.saveCustomer(any(Customer.class))).thenReturn(customer);

    	mockMvc.perform(put("/admin/customer/edit")
				.contentType(MediaType.APPLICATION_JSON)
				.content(new ObjectMapper().writeValueAsString(customer)))
				.andExpect(status().isOk())
				.andExpect(content().json(new ObjectMapper().writeValueAsString(customerService.saveCustomer(customer))))
				.andDo(print());

	}

	@Test
	@WithMockUser(authorities = "NORMAL")
	public void editCustomerWhenNormalCustomerShouldReturn403() throws Exception {

    	mockMvc.perform(put("/admin/customer/edit"))
				.andExpect(status().isForbidden())
				.andReturn();

	}

	@Test
	@WithAnonymousUser
	public void editCustomerWhenNotAuthenticatedShouldReturn401() throws Exception {

		mockMvc.perform(put("/admin/customer/edit"))
				.andExpect(status().isUnauthorized())
				.andReturn();

	}

	@Test
	@WithMockUser
	public void getCustomerByIdShouldReturnCustomer() throws Exception {
    	long id = 1L;
    	Mockito.when(customerService.findCustomerById(id)).thenReturn(getDummyCustomer());

    	mockMvc.perform(get("/admin/customer/{id}", id))
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
				.andExpect(content().json(new ObjectMapper().writeValueAsString(
						customerService.findCustomerById(id)
				)))
				.andDo(print())
				.andReturn();

	}

	@Test
	@WithMockUser(authorities = "NORMAL")
	public void getCustomerByIdWhenNormalCustomerShouldReturn403() throws Exception {

    	mockMvc.perform(get("/admin/customer/{id}", 1))
				.andExpect(status().isForbidden());

	}

	@Test
	@WithAnonymousUser
	public void getCustomerByIdWhenNotAuthenticatedShouldReturn401() throws Exception {

    	mockMvc.perform(get("/admin/customer/{id}", 1))
				.andExpect(status().isUnauthorized());

	}

    @Test
	@WithMockUser
    public void getCurrencies() throws Exception {

        List<CurrencyUnit> currencyUnits = new ArrayList<>();
        currencyUnits.add(MonetaryCurrencies.getCurrency(Locale.UK));
        currencyUnits.add(MonetaryCurrencies.getCurrency(Locale.CANADA));
        currencyUnits.add(MonetaryCurrencies.getCurrency(Locale.US));

        Mockito.when(customerService.getCurrencies()).thenReturn(currencyUnits);

        mockMvc.perform(get("/currencies"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(content().json(new ObjectMapper().writeValueAsString(currencyUnits)))
                .andDo(print());

    }

	@Test
	@WithAnonymousUser
	public void getCurrenciesWhenNotAuthenticatedShouldReturn401() throws Exception {

		List<CurrencyUnit> currencyUnits = new ArrayList<>();
		currencyUnits.add(MonetaryCurrencies.getCurrency(Locale.UK));
		currencyUnits.add(MonetaryCurrencies.getCurrency(Locale.CANADA));
		currencyUnits.add(MonetaryCurrencies.getCurrency(Locale.US));

		Mockito.when(customerService.getCurrencies()).thenReturn(currencyUnits);

		mockMvc.perform(get("/currencies"))
				.andExpect(status().isUnauthorized())
				.andReturn();

	}

    @Test
	@WithMockUser
    public void convertCurrencyShouldReturnConvertedValue() throws Exception {

        Mockito.when(customerService.convertCurrency("USD", "GBP", 100.0)).thenReturn(200.0);

        mockMvc.perform(get("/currency/USD/GBP/100"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(content().string("200.0"))
                .andDo(print())
                .andReturn();

    }

    @Test
	@WithAnonymousUser
	public void convertCurrencyWhenNotAuthenticatedShouldReturn401() throws Exception {

    	Mockito.when(customerService.convertCurrency("USD", "GBP", 100.0)).thenReturn(200.0);

    	mockMvc.perform(get("/currency/USD/GBP/100"))
				.andExpect(status().isUnauthorized())
				.andReturn();

	}



    private Customer getDummyCustomer() {
        Customer customer = new Customer();
        customer.setFirstName("Stefan");
        customer.setLastName("Kondinski");
        customer.setEmail("kondinskis@gmail.com");
        customer.setEmbg("1234123412341");
        customer.setBalance(1234.4);
        customer.setAddress("mukos");
        customer.setPassword("123");
        customer.setId(1L);
        return customer;
    }

}
