package com.belatrixsf.app.service;

import com.belatrixsf.app.dao.entity.Customer;
import com.belatrixsf.app.dao.repository.CustomerRepository;
import java.util.ArrayList;
import java.util.List;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

@Service
public class CustomerServiceImpl implements CustomerService {

  private final CustomerRepository customerRepository;


  public CustomerServiceImpl(CustomerRepository customerRepository) {
    this.customerRepository = customerRepository;
  }


  @Override
  public Customer getCustomerId(long id) {
    Customer customer = customerRepository.findById(id);
    if (customer == null) {
      throw new CustomerNotFoundException(id);
    }
    return customer;
  }

  @Override
  public List<Customer> findAllCustomers() {
    ArrayList<Customer> customers = new ArrayList<>();
    customerRepository.findAll().forEach(customers::add);
    return customers;
  }

  @Override
  public List<Customer> findCustomersByLastName(String lastName) {
    return customerRepository.findByLastName(lastName);
  }

  @Override
  public Customer saveCustomer(Customer customer) {
    return customerRepository.save(customer);
  }

  @Override
  public void updateCustomer(Customer customer) {
    if (!customerRepository.existsById(customer.getId())) {
      throw new CustomerNotFoundException(customer.getId());
    }
    customerRepository.save(customer);
  }

  @PreAuthorize("hasAuthority('ROLE_ADMIN')")
  @Override
  public void deleteCustomerById(long id) {
    if (!customerRepository.existsById(id)) {
      throw new CustomerNotFoundException(id);
    }
    customerRepository.deleteById(id);
  }

}
