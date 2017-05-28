package com.fict.services;

import com.fict.entities.Customer;
import com.fict.entities.Role;
import com.fict.repository.CustomerRepository;
import com.fict.repository.RoleRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Java6Assertions.assertThat;

/**
 * Created by stevo on 5/29/17.
 */
@RunWith(SpringRunner.class)
public class CustomerServiceTest {

    @TestConfiguration
    static class CustomerServiceTestContextConfiguration {
        @Bean
        public CustomerService customerService() {
            return new CustomerServiceImpl();
        }
    }

    @Autowired
    private CustomerService customerService;

    @MockBean
    private CustomerRepository customerRepository;

    @MockBean
    private RoleRepository roleRepository;

    @Before
    public void setUp() {
        Customer customer = new Customer();
        customer.setEmail("kondinskis@gmail.com");

        Mockito.when(customerRepository.findCustomerByEmail(customer.getEmail()))
                .thenReturn(customer);

        Role role = new Role();
        role.setId(1L);
        role.setName("ADMIN");

        Mockito.when(roleRepository.findRoleByName("ADMIN"))
                .thenReturn(role);
    }

    @Test
    public void whenValidEmail_thenCustomerShouldBeFound() {
        String email = "kondinskis@gmail.com";
        Customer found = customerService.findCustomerByEmail(email);

        assertThat(found.getEmail())
                .isEqualTo(email);
    }

}
