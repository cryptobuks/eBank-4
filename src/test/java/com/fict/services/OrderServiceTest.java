package com.fict.services;

import com.fict.entities.Creditor;
import com.fict.entities.Customer;
import com.fict.entities.Order;
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
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Java6Assertions.assertThat;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

/**
 * Created by Dule on 31-May-17.
 */
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
    public void findOrdersByCrediotShouldReturnOrders() {

        List<Order> shouldReturn = new ArrayList<>();
        Creditor creditor = getDummyCreditor();
        Mockito.when(orderRepository.findOrdersByCreditor(creditor)).thenReturn(shouldReturn);

        List<Order> foundOrders = orderService.findOrdersByCreditor(creditor);

        assertTrue(foundOrders.equals(shouldReturn));
    }

    @Test
    public void findOrdersByCrediotWhenNotFoundShouldReturnNull() {

        Creditor creditor = getDummyCreditor();
        Mockito.when(orderRepository.findOrdersByCreditor(creditor)).thenReturn(null);

        List<Order> foundOrders = orderService.findOrdersByCreditor(creditor);

        assertNull(foundOrders);
    }

    public Order getDummyOrder() {
        Customer customer = new Customer();
        customer.setId(1L);

        Creditor creditor = new Creditor();
        creditor.setId(1L);

        Order order = new Order();
        order.setCustomer(customer);
        order.setCreditor(creditor);
        order.setAmount(5.00D);
        order.setDescription("Test order");
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
