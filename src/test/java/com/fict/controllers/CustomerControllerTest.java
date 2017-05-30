package com.fict.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fict.entities.Customer;
import com.fict.services.CustomerService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.TestingAuthenticationToken;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import javax.money.CurrencyUnit;
import javax.money.MonetaryCurrencies;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
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
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        User user = new User("kondinskis@gmail.com", "", AuthorityUtils.createAuthorityList("NORMAL"));
        testingAuthenticationToken = new TestingAuthenticationToken(user, null);
        SecurityContextHolder.getContext().setAuthentication(testingAuthenticationToken);
    }

    @Test
    public void getAuthenticatedCustomerShouldReturnCustomer() throws Exception {

        Mockito.when(customerService.findCustomerByEmail("kondinskis@gmail.com")).thenReturn(getDummyCustomer());

        mockMvc.perform(get("/customer").principal(testingAuthenticationToken))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(content().json(new ObjectMapper().writeValueAsString(
                        customerService.findCustomerByEmail(testingAuthenticationToken.getName()))))
                .andDo(print())
                .andReturn();

    }

    @Test
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
    public void convertCurrencyShouldReturnConvertedValue() throws Exception {

        Mockito.when(customerService.convertCurrency("USD", "GBP", 100.0)).thenReturn(200.0);

        mockMvc.perform(get("/currency/USD/GBP/100"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(content().string("200.0"))
                .andDo(print())
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
        customer.setId(1L);
        return customer;
    }

}
