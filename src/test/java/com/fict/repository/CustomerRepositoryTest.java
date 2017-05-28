package com.fict.repository;

import com.fict.entities.Customer;
import com.fict.repository.CustomerRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Java6Assertions.assertThat;

/**
 * Created by stevo on 5/28/17.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class CustomerRepositoryTest {

    @Autowired
    private CustomerRepository customerRepository;

    // vo slucaj na postoecki korisnik
    @Test
    public void whenFindByEmail_thenReturnCustomer() {
        Customer customer = new Customer();
        customer.setEmail("kondinskis@gmail.com");

        Customer found = customerRepository.findCustomerByEmail(customer.getEmail());

        assertThat(found.getEmail()).isEqualTo(customer.getEmail());
    }


}
