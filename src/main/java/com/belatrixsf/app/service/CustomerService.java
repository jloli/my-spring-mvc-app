package com.belatrixsf.app.service;

import com.belatrixsf.app.dao.entity.Customer;
import java.util.List;

public interface CustomerService {

  Customer getCustomerId(long id);

  Customer saveCustomer(Customer customer);

  void updateCustomer(Customer customer);

  void deleteCustomerById(long id);

  List<Customer> findAllCustomers();

  List<Customer> findCustomersByLastName(String lastName);

}
