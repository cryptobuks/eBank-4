package com.fict.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fict.entities.Customer;
import com.fict.services.CustomerService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Matchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.TestingAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import javax.money.CurrencyUnit;
import javax.money.MonetaryCurrencies;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import static org.mockito.Matchers.any;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@WebMvcTest(CustomerController.class)
public class CustomerControllerTest {


    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CustomerService customerService;

    @Before
    public void setUp() {
        mockMvc = MockMvcBuilders
				.webAppContextSetup(webApplicationContext)
				.apply(springSecurity())
				.build();
	}

	@Test
	@WithMockUser
	public void testShouldReturnPricipal() throws Exception {

    	String s = SecurityContextHolder.getContext().getAuthentication().getName();


    	mockMvc.perform(get("/user"))
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
				.andExpect(jsonPath("$.principal.username", org.hamcrest.Matchers.is(s)))
				.andDo(print())
				.andReturn();

	}

	@Test
	@WithAnonymousUser
	public void testIfNotAuthenticatedShouldReturn401() throws Exception {

    	mockMvc.perform(get("/user"))
				.andExpect(status().isUnauthorized())
				.andDo(print())
				.andReturn();

	}


    @Test
	@WithMockUser("kondinskis@gmail.com")
    public void getAuthenticatedCustomerShouldReturnCustomer() throws Exception {

    	String email = SecurityContextHolder.getContext().getAuthentication().getName();

    	Customer shouldReturn = getDummyCustomer();
		Mockito.when(customerService.findCustomerByEmail(email)).thenReturn(shouldReturn);

        mockMvc.perform(get("/customer"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(content().json(new ObjectMapper().writeValueAsString(shouldReturn)))
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
				.andExpect(content().json(new ObjectMapper().writeValueAsString(customer)))
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
	public void getAllCustomersShouldReturnCustomers() throws Exception {

    	Page<Customer> shouldReturn = new PageImpl<>(Arrays.asList(
    			getDummyCustomer(),
    			getDummyCustomer(),
    			getDummyCustomer(),
    			getDummyCustomer(),
    			getDummyCustomer()
		));

		Mockito.when(customerService.findAll(any(Integer.class), any(Integer.class))).thenReturn(shouldReturn);


    	mockMvc.perform(get("/admin/customers")
				.param("p", "1")
				.param("l", "5"))
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
				.andExpect(content().json(new ObjectMapper().writeValueAsString(shouldReturn)))
				.andDo(print())
				.andReturn();


	}

	@Test
	@WithMockUser(authorities = "NORMAL")
	public void getAllCustomersWhenNormalCustomerShouldReturnForbidden() throws Exception {

		mockMvc.perform(get("/admin/customers"))
				.andExpect(status().isForbidden())
				.andReturn();

	}

	@Test
	@WithAnonymousUser
	public void getAllCustomersWhenNotAuthenticatedShouldReturn401() throws Exception {

		mockMvc.perform(get("/admin/customers"))
				.andExpect(status().isUnauthorized())
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
				.andExpect(content().json(new ObjectMapper().writeValueAsString(customer)))
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
    	Customer shouldReturn = getDummyCustomer();
    	Mockito.when(customerService.findCustomerById(id)).thenReturn(shouldReturn);

    	mockMvc.perform(get("/admin/customer/{id}", id))
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
				.andExpect(content().json(new ObjectMapper().writeValueAsString(
						shouldReturn
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
