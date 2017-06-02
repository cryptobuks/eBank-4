package com.fict.services;

import com.fict.entities.Customer;
import com.fict.entities.Order;
import com.fict.entities.Role;
import com.fict.repository.CustomerRepository;
import com.fict.repository.RoleRepository;
import org.javamoney.moneta.Money;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.*;
import org.springframework.test.context.junit4.SpringRunner;

import javax.money.CurrencyUnit;
import javax.money.MonetaryAmount;
import javax.money.MonetaryCurrencies;
import javax.money.convert.CurrencyConversion;
import javax.money.convert.MonetaryConversions;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import static org.assertj.core.api.Java6Assertions.assertThat;
import static org.mockito.Mockito.mock;

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
        Customer shouldReturn = mock(Customer.class);
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
        Customer shouldReturn = mock(Customer.class);
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
        Customer shouldReturn = mock(Customer.class);
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


    @Test
    public void findAllCustomersShouldReturnCustomers() {

        Pageable request = new PageRequest(0, 2, Sort.Direction.ASC, "id");
        List<Customer> customerList = Arrays.asList(
                mock(Customer.class),
                mock(Customer.class),
                mock(Customer.class));
        Page<Customer> shouldReturn = new PageImpl<>(customerList, request, 3);

        Mockito.when(customerRepository.findAll(request)).thenReturn(shouldReturn);

        Page<Customer> foundOrders = customerService.findAll(1,2);

        assertThat(foundOrders).isEqualTo(shouldReturn);
    }

    @Test
    public void findAllCustomersWhenNotFoundShouldReturnNull() {

        PageRequest request = new PageRequest(0, 5, Sort.Direction.ASC, "id");
        Mockito.when(customerRepository.findAll(request)).thenReturn(null);

        Page<Customer> foundCustomers = customerService.findAll(1, 5);

        assertThat(foundCustomers).isEqualTo(null);
    }

    @Test
	public void registerCustomerIfSuccessShouldReturnCustomer() {

    	Customer shouldReturn = mock(Customer.class);

    	Mockito.when(customerRepository.save(shouldReturn)).thenReturn(shouldReturn);

    	Customer found = customerService.registerCustomer(shouldReturn);

    	assertThat(found).isEqualTo(shouldReturn);
	}

	@Test(expected = DataIntegrityViolationException.class)
	public void registerCustomerIfEmbgInUseShouldThrowException() {

    	Customer registered = getDummyCustomer();
    	Mockito.when(customerRepository.findCustomerByEmbg("1234123412341")).thenReturn(registered);

    	Customer shouldReturn = mock(Customer.class);
		Mockito.when(shouldReturn.getEmbg()).thenReturn("1234123412341");
    	Mockito.when(customerRepository.save(shouldReturn))
				.thenReturn(shouldReturn);

    	Customer found = customerService.registerCustomer(shouldReturn);

		assertThat(found).isEqualTo(shouldReturn);

	}

	@Test
	public void saveCustomerShouldReturnSavedCustomer() {

    	Customer customer = getDummyCustomer();

    	Mockito.when(roleRepository.findRoleByName(customer.getRole().getName()))
				.thenReturn(customer.getRole());
		Mockito.when(customerRepository.findCustomerById(1L))
				.thenReturn(customer);

		Customer toSaveCustomer = customerService.findCustomerById(1L);

    	customerService.saveCustomer(toSaveCustomer);

    	assertThat(customer).isEqualTo(toSaveCustomer);

	}

	@Test
	public void convertCurrencyShouldReturnValue() {

		String from = "EUR";
		String to = "USD";
		Double amount = 200.0;

		Double returned = customerService.convertCurrency(from, to, amount);
		Double shouldReturn = 224.38;

		assertThat(returned).isEqualTo(shouldReturn);

	}

	@Test
	public void getCurrencies() {
    	List<CurrencyUnit> shouldReturn = Arrays.asList(
				MonetaryCurrencies.getCurrency(Locale.CANADA),
				MonetaryCurrencies.getCurrency(Locale.US),
				MonetaryCurrencies.getCurrency(Locale.UK),
				MonetaryCurrencies.getCurrency(Locale.JAPAN),
				MonetaryCurrencies.getCurrency(Locale.GERMANY)
		);

		List<CurrencyUnit> found = customerService.getCurrencies();

		assertThat(found).isEqualTo(shouldReturn);
	}

	private Customer getDummyCustomer() {
		Customer customer = new Customer();
		customer.setEmail("kondinskis@gmail.com");
		customer.setId(1L);
		customer.setAddress("Mukos");
		customer.setTransactionNumber("1234123412341234");
		customer.setFirstName("Stefan");
		customer.setLastName("Kondinski");
		customer.setEmbg("1234123412341");
		Role role = new Role();
		role.setId(1L);
		role.setName("ADMIN");
		customer.setRole(role);
		return customer;
	}
}
