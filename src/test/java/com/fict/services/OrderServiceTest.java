package com.fict.services;

import com.fict.entities.Creditor;
import com.fict.entities.Customer;
import com.fict.entities.Order;
import com.fict.entities.Role;
import com.fict.repository.CreditorRepository;
import com.fict.repository.CustomerRepository;
import com.fict.repository.OrderRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.*;
import org.springframework.test.context.junit4.SpringRunner;
import sun.security.acl.PrincipalImpl;

import java.security.Principal;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Java6Assertions.assertThat;
import static org.mockito.Matchers.any;


@RunWith(SpringRunner.class)
public class OrderServiceTest {

    @TestConfiguration
    static class OrderServiceTestContextConfiguration {
        @Bean
        public OrderService orderService() {
            return new OrderServiceImpl();
        }
    }

    @Autowired
    private OrderService orderService;

    @MockBean
    private OrderRepository orderRepository;

    @MockBean
    private CustomerRepository customerRepository;

    @MockBean
    private CreditorRepository creditorRepository;

    @Before
    public void setUp() {

    }

    @Test
    public void findOrdersByCreditorShouldReturnOrders() {

        List<Order> shouldReturn = new ArrayList<>();
        Creditor creditor = getDummyCreditor();
        Mockito.when(orderRepository.findOrdersByCreditor(creditor)).thenReturn(shouldReturn);

        List<Order> foundOrders = orderService.findOrdersByCreditor(creditor);

        assertThat(foundOrders).isEqualTo(shouldReturn);
    }

    @Test
    public void findOrdersByCreditorWhenNotFoundShouldReturnNull() {

        Creditor creditor = getDummyCreditor();
        Mockito.when(orderRepository.findOrdersByCreditor(creditor)).thenReturn(null);

        List<Order> foundOrders = orderService.findOrdersByCreditor(creditor);

        assertThat(foundOrders).isEqualTo(null);
    }

    @Test
    public void findOrdersByCustomerEmailShouldReturnOrders() {

        String email = "kondinskis@gmail.com";
        List<Order> shouldReturn = new ArrayList<>();
        Mockito.when(orderRepository.findOrdersByCustomerEmail(email)).thenReturn(shouldReturn);

        List<Order> found = orderService.findOrdersByCustomerEmail(email);

        assertThat(found).isEqualTo(shouldReturn);
    }

    @Test
    public void findOrdersByCustomerEmailWhenNotFoundShouldReturnNull() {

        String email = "kondinskis@gmail.com";
        Mockito.when(orderRepository.findOrdersByCustomerEmail(email)).thenReturn(null);

        List<Order> found = orderService.findOrdersByCustomerEmail(email);

        assertThat(found).isEqualTo(null);
    }

    @Test
    public void findOrderByIdShouldReturnOrder() {

        long id = 1L;
        Order shouldReturn = getDummyOrder();
        Mockito.when(orderRepository.findOrderById(id)).thenReturn(shouldReturn);

        Order found = orderService.findOrderById(id);

        assertThat(found).isEqualTo(shouldReturn);

    }

    @Test
    public void findOrderByIdWhenNotFoundShouldReturnNull() {

        long id = 1L;
        Mockito.when(orderRepository.findOrderById(id)).thenReturn(null);

        Order found = orderService.findOrderById(id);

        assertThat(found).isEqualTo(null);

    }

	@Test
	public void saveOrderShouldReturnOrder() {

		Order shouldReturn = getDummyOrder(1, 2, 10, 1);
		Order order = getDummyOrder(1, 2, 10, 1);
		Principal principal = new PrincipalImpl("test@test.com");

		Mockito.when(orderRepository.findOrderById(any(Long.class))).thenReturn(order);
		Mockito.when(customerRepository.findCustomerByEmail(any(String.class)))
				.thenReturn(shouldReturn.getCustomer());
		Mockito.when(customerRepository.findCustomerByTransactionNumber(any(String.class)))
				.thenReturn(shouldReturn.getCustomer());
		Mockito.when(creditorRepository.findCreditorByTransactionNumber(any(String.class)))
				.thenReturn(shouldReturn.getCreditor());
		Mockito.when(orderRepository.save(any(Order.class))).thenReturn(shouldReturn);

		Order savedOrder = orderService.saveOrder(order, principal);

		assertThat(savedOrder).isEqualTo(shouldReturn);

	}

    @Test
    public void findAllOrdersShouldReturnOrders() {

        Pageable request = new PageRequest(0, 2, Sort.Direction.DESC, "id");
        Page<Order> shouldReturn = new PageImpl<Order>(Arrays.asList(
                getDummyOrder(1, 2, 1, 1),
                getDummyOrder(2, 1, 2, 2),
                getDummyOrder(3, 2, 1, 3)));

        Mockito.when(orderRepository.findAll(request)).thenReturn(shouldReturn);

        Page<Order> foundOrders = orderService.findAll(1, 2);

        assertThat(foundOrders).isEqualTo(shouldReturn);

    }

    @Test
    public void findAllOrdersWhenNotFoundShouldReturnNull() {

        PageRequest request = new PageRequest(0, 5, Sort.Direction.ASC, "id");
        Mockito.when(orderRepository.findAll(request)).thenReturn(null);

        Page<Order> foundOrders = orderService.findAll(1, 5);

        assertThat(foundOrders).isEqualTo(null);

    }

    @Test
    public void editOrderShouldReturnOrder() {

        Order shouldReturn = getDummyOrder(1, 2, 10, 1);
        Order order = getDummyOrder(1, 2, 10, 1);
        Mockito.when(orderRepository.findOrderById(any(Long.class))).thenReturn(order);
        Mockito.when(orderRepository.save(order)).thenReturn(shouldReturn);

        Order editedOrder = orderService.editOrder(order);

        assertThat(editedOrder).isEqualTo(shouldReturn);

    }

    @Test
    public void findOrdersByUserShouldReturnOrders() {

        Page<Order> shouldReturn = new PageImpl<Order>(Arrays.asList(
                getDummyOrder(1, 2, 1, 1),
                getDummyOrder(2, 1, 2, 2),
                getDummyOrder(3, 2, 1, 3)));
        Customer customer = new Customer();
        Principal principal = new PrincipalImpl("test@test.com");
        PageRequest request = new PageRequest(0, 2, Sort.Direction.DESC, "id");

        Mockito.when(customerRepository.findCustomerByEmail("test@test.com")).thenReturn(customer);
        Mockito.when(orderRepository.findOrdersByCustomer(customer, request)).thenReturn(shouldReturn);

        Page<Order> foundOrders = orderService.findOrdersByUser(1, 2, principal);

        assertThat(foundOrders).isEqualTo(shouldReturn);

    }


    public Order getDummyOrder() {
        Customer customer = new Customer();
        customer.setId(1L);
        customer.setBalance(100D);


        Creditor creditor = new Creditor();
        creditor.setId(1L);

        Order order = new Order();
        order.setCustomer(customer);
        order.setCreditor(creditor);
        order.setAmount(5.00D);
        order.setDescription("Test order");
        return order;
    }

    public Order getDummyOrder(long customerId, long creditorId, double amount, long orderId) {
        Customer customer = new Customer();
        customer.setId(customerId);
        customer.setEmail("test@test.com");
        customer.setBalance(100D);
        customer.setFirstName("Tester");
        customer.setLastName("Testerovski");
        customer.setActive(true);
        customer.setEmbg("0000000000000");
        customer.setPassword("test");
        Role role = new Role();
        role.setId(2L);
        role.setName("NORMAL");
        customer.setRole(role);
        customer.setAddress("test");
        customer.setTransactionNumber("0000000000000000");


        Creditor creditor = new Creditor();
        creditor.setId(creditorId);
        creditor.setName("CreditorTest");
        creditor.setImeNaBanka("EBANK");
        creditor.setTransactionNumber("0000000000000001");
        creditor.setAddress("Bitola");


        Order order = new Order();
        order.setId(orderId);
        order.setCustomer(customer);
        order.setCreditor(creditor);
        order.setAmount(amount);
        order.setDescription("Test order");
        order.setDate(Date.valueOf("2017-06-05"));
        order.setType("Transfer");


        return order;
    }

    public Creditor getDummyCreditor() {
        Creditor creditor = new Creditor();
        creditor.setName("Stefan");
        creditor.setAddress("Bitola, Macedonia");
        creditor.setImeNaBanka("EBANK");
        return creditor;
    }
}
