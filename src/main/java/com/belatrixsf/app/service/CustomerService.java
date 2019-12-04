package com.belatrixsf.app.service;

import com.belatrixsf.app.dao.entity.Customer;
import java.util.List;

public interface CustomerService {

  Customer addCustomer(Customer customer);

  Customer getCustomerId(long id);

  List<Customer> findAllCustomers();

  List<Customer> findCustomersByLastName(String lastName);



}
