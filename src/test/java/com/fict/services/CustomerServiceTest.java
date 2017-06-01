package com.fict.services;

import com.fict.entities.Customer;
import com.fict.entities.Order;
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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
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

    }

    @Test
    public void findCustomerByEmailShouldReturnCustomer() {

        String email = "kondinskis@gmail.com";
        Customer shouldReturn = getDummyCustomer();
        Mockito.when(customerRepository.findCustomerByEmail(email)).thenReturn(shouldReturn);

        Customer found = customerService.findCustomerByEmail(email);

        assertThat(found)
                .isEqualTo(shouldReturn);
    }

    @Test
    public void findCustomerByEmailWhenNotFoundShouldReturnNull() {

        String email = "test@mail.com";

        Mockito.when(customerRepository.findCustomerByEmail(email)).thenReturn(null);

        Customer found = customerService.findCustomerByEmail(email);

        assertThat(found)
                .isEqualTo(null);

    }

    @Test
    public void findCustomerByEmbgShouldReturnCustomer() {

        String embg = "1234123412341";
        Customer shouldReturn = getDummyCustomer();
        Mockito.when(customerRepository.findCustomerByEmbg(embg)).thenReturn(shouldReturn);

        Customer found = customerService.findCustomerByEmbg(embg);

        assertThat(found).isEqualTo(shouldReturn);

    }

    @Test
    public void findCustomerByEmbgWhenNotFoundShouldReturnNull() {

        String embg = "4321432143212";
        Mockito.when(customerRepository.findCustomerByEmbg(embg)).thenReturn(null);

        Customer found = customerService.findCustomerByEmbg(embg);

        assertThat(found).isEqualTo(null);

    }

    @Test
    public void findCustomerByIdShouldReturnCustomer() {

        long id = 1L;
        Customer shouldReturn = getDummyCustomer();
        Mockito.when(customerRepository.findCustomerById(id)).thenReturn(shouldReturn);

        Customer found = customerService.findCustomerById(id);

        assertThat(found).isEqualTo(shouldReturn);

    }

    @Test
    public void findCustomerByIdWhenNotFoundShouldReturnNull() {

        long id = 1L;
        Mockito.when(customerRepository.findCustomerById(id)).thenReturn(null);

        Customer found = customerService.findCustomerById(id);

        assertThat(found).isEqualTo(null);

    }

    /*
        @Test
         public void findAllCustomersShouldReturnCustomers() {

             PageRequest request = new PageRequest(0, 5, Sort.Direction.ASC, "id");
             Page<Customer> shouldReturn;

             Mockito.when(customerRepository.findAll(request)).thenReturn(shouldReturn);

             Page<Customer> foundCustomers = customerService.findAll(1,5);

             assertThat(foundCustomers).isEqualTo(shouldReturn);
         }
    */
    @Test
    public void findAllCustomersWhenNotFoundShouldReturnNull() {

        PageRequest request = new PageRequest(0, 5, Sort.Direction.ASC, "id");
        Mockito.when(customerRepository.findAll(request)).thenReturn(null);

        Page<Customer> foundCustomers = customerService.findAll(1, 5);

        assertThat(foundCustomers).isEqualTo(null);
    }

    private Customer getDummyCustomer() {
        Customer customer = new Customer();
        customer.setEmail("kondinskis@gmail.com");
        customer.setFirstName("Stefan");
        customer.setLastName("Kondinski");
        customer.setEmbg("1234123412341");
        return customer;
    }

}
