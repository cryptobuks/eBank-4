package com.fict.repository;

import com.fict.entities.Customer;
import com.fict.entities.Role;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Java6Assertions.assertThat;

//@RunWith(SpringRunner.class)
//@DataJpaTest
//@SpringBootTest(properties = "flyway.enabled=false")
//public class CustomerRepositoryTest {
//
//    @Autowired
//    private TestEntityManager entityManager;
//
//    @Autowired
//    private CustomerRepository customerRepository;
//
//    @Autowired
//    private RoleRepository roleRepository;
//
//    @Before
//    public void setUp() {
//        Customer dummy = getDummyCustomer();
//        Role role = new Role();
//        role.setId(1L);
//        role.setName("ADMIN");
//        roleRepository.save(role);
//        customerRepository.save(dummy);
//    }
//
//    @Test
//    public void findCustomerByEmailShouldReturnCustomer() {
//
//        String email = "kondinskis@gmail.com";
//        Customer shouldReturn = getDummyCustomer();
//        Customer found = customerRepository.findCustomerByEmail(email);
//
//        assertThat(found.getRole()).isEqualToComparingFieldByField(shouldReturn.getRole());
//        assertThat(found).isEqualToIgnoringGivenFields(shouldReturn, "password", "role");
//
//    }
//
//    @Test
//    public void findCustomerByEmailWhenNotFoundShouldReturnNull() {
//
//        String email = "test@mail.com";
//        Customer found = customerRepository.findCustomerByEmail(email);
//        System.out.println(found);
//        assertThat(found).isEqualTo(null);
//
//    }
//
//
//    private Customer getDummyCustomer() {
//        Customer customer = new Customer();
//        customer.setEmail("kondinskis@gmail.com");
//        customer.setEmbg("1234123412341");
//        customer.setTransactionNumber("1234123412341234");
//        customer.setFirstName("Stefan");
//        customer.setLastName("Kondinski");
//        customer.setBalance(0.0);
//        Role role = new Role();
//        role.setId(1L);
//        role.setName("ADMIN");
//        customer.setRole(role);
//        customer.setAddress("MUKOS");
//        customer.setActive(true);
//        customer.setPassword("123");
//        customer.setId(1L);
//        return customer;
//    }
//
//}
