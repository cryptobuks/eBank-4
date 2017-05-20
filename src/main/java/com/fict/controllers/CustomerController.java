package com.fict.controllers;

import java.security.Principal;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;

import javax.money.CurrencyUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.web.bind.annotation.*;

import com.fict.entities.Customer;
import com.fict.entities.Order;
import com.fict.services.CreditorService;
import com.fict.services.CustomerService;
import com.fict.services.OrderService;

@RestController
public class CustomerController {
	
	@Autowired
	private CustomerService customerService;
	
	@RequestMapping("/user")
	public Principal test(Principal principal){
		return principal;
	}
	
	@RequestMapping("/customer")
    public Customer getAuthenticatedCustomer(Principal principal){
		
	    return customerService.findCustomerByEmail(principal.getName());
    }
	
	@RequestMapping(value = "/customer/register", method = RequestMethod.POST)
	public Customer registerCustomer(@RequestBody Customer customer){
		
		return customerService.registerCustomer(customer);
	}
	
	@RequestMapping("/admin/customer/{id}")
	public Customer getCustomer(@PathVariable Long id) {
		
		return customerService.findCustomerById(id);
	}
	
	@RequestMapping("/admin/customers")
	public Page<Customer> getAllCustomers(@RequestParam(name = "p", defaultValue = "1") int pageNumber,
			@RequestParam(name = "l", defaultValue = "5") int limit) {

		return customerService.findAll(pageNumber, limit);
	}

    @RequestMapping(value = "/admin/customer/edit", method = RequestMethod.PUT)
    public Customer editCustomer(@RequestBody Customer customer){
    	
	    return customerService.saveCustomer(customer);
    }
    
    @RequestMapping(value = "/currency/{from}/{to}/{value}")
    public Double getConvertedCurrencyValue(@PathVariable("from") String from, 
    		@PathVariable("to") String to, @PathVariable("value") Double value){
    	
    	return customerService.convertCurrency(from, to, value);
    }
    
    @RequestMapping(value = "/currencies")
    public List<CurrencyUnit> getCurrencies(){
    	
    	return customerService.getCurrencies();
    }


}

