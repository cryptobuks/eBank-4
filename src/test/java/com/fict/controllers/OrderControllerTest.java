package com.fict.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fict.entities.Creditor;
import com.fict.entities.Customer;
import com.fict.entities.Order;
import com.fict.services.OrderService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.*;
import org.springframework.http.MediaType;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.security.Principal;
import java.util.Arrays;

import static org.mockito.Matchers.any;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Created by Dule on 03-Jun-17.
 */

@RunWith(SpringRunner.class)
@WebMvcTest(OrderController.class)
public class OrderControllerTest {

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private OrderService orderService;

    @Before
    public void setUp() {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(webApplicationContext)
                .apply(springSecurity())
                .build();
    }

    @Test
    @WithMockUser
    public void getOrderByIdShouldReturnOrder() throws Exception {

        long id = 1L;
        Order shouldReturn = getDummyOrder(1, 2, 5, 1);

        Mockito.when(orderService.findOrderById(id)).thenReturn(shouldReturn);

        mockMvc.perform(get("/admin/order/{id}", id))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(content().json(new ObjectMapper().writeValueAsString(shouldReturn)))
                .andDo(print())
                .andReturn();
    }

    @Test
    @WithMockUser
    public void getAllOrdersShouldReturnOrders() throws Exception {

        Page<Order> shouldReturn = new PageImpl<Order>(Arrays.asList(
                getDummyOrder(1, 2, 5, 1),
                getDummyOrder(2, 1, 10, 2),
                getDummyOrder(3, 1, 15, 3)
        ));

        Mockito.when(orderService.findAll(any(Integer.class), any(Integer.class))).thenReturn(shouldReturn);

        mockMvc.perform(get("/admin/orders")
                .param("p", "1")
                .param("l", "2"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(content().json(new ObjectMapper().writeValueAsString(shouldReturn)))
                .andDo(print())
                .andReturn();
    }

    @Test
    @WithMockUser
    public void getOrdersByUserShouldReturnOrders() throws Exception {

        Principal principal = SecurityContextHolder.getContext().getAuthentication();
        Page<Order> shouldReturn = new PageImpl<Order>(Arrays.asList(
                getDummyOrder(1, 2, 5, 1),
                getDummyOrder(2, 1, 10, 2),
                getDummyOrder(3, 1, 15, 3)
        ));

        Mockito.when(orderService.findOrdersByUser(1, 2, principal))
                .thenReturn(shouldReturn);

        mockMvc.perform(get("/orders/user")
                .param("p", "1")
                .param("l", "2"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(content().json(new ObjectMapper().writeValueAsString(shouldReturn)))
                .andDo(print())
                .andReturn();
    }

    @Test
    @WithMockUser
    public void editOrderShouldReturnOrder() throws Exception {

        Order shouldReturn = getDummyOrder(1, 2, 10, 1);

        Mockito.when(orderService.editOrder(any(Order.class))).thenReturn(shouldReturn);

        mockMvc.perform(put("/admin/order/edit")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(shouldReturn)))
                .andExpect(status().isOk())
                .andExpect(content().json(new ObjectMapper().writeValueAsString(shouldReturn)))
                .andDo(print());
    }
    /*
    @Test
    @WithMockUser
    public void saveOrderShouldReturnOrder() throws Exception {

        Principal principal = SecurityContextHolder.getContext().getAuthentication();
        Order shouldReturn = getDummyOrder(1, 2, 10, 1);
        Order order = getDummyOrder(1,3,5,2);

        Mockito.when(orderService.saveOrder(shouldReturn, principal)).thenReturn(shouldReturn);

        mockMvc.perform(post("/order/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(shouldReturn)))
                .andExpect(status().isOk())
                //  .andExpect(content().json(new ObjectMapper().writeValueAsString(shouldReturn)))
                .andDo(print());
    }
    */

    public Order getDummyOrder(long customerId, long creditorId, double amount, long orderId) {
        Customer customer = new Customer();
        customer.setId(customerId);

        Creditor creditor = new Creditor();
        creditor.setId(creditorId);

        Order order = new Order();
        order.setId(orderId);
        order.setCustomer(customer);
        order.setCreditor(creditor);
        order.setAmount(amount);
        order.setDescription("Test order");
        return order;
    }
}
