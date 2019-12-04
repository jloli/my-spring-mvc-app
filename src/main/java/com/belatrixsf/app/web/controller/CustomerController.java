package com.belatrixsf.app.web.controller;

import com.belatrixsf.app.dao.entity.Customer;
import com.belatrixsf.app.service.CustomerNotFoundException;
import com.belatrixsf.app.service.CustomerService;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/customers")
public class CustomerController {

  private static final Logger log = LoggerFactory.getLogger(CustomerController.class);


  private final CustomerService customerService;


  public CustomerController(CustomerService customerService) {
    this.customerService = customerService;
  }

  @GetMapping
  public List<Customer> findAllCustomers() {
    return customerService.findAllCustomers();
  }

  @GetMapping("/{customerId}")
  public Customer getCustomerById(@PathVariable long customerId) {
    return customerService.getCustomerId(customerId);
  }

  @GetMapping(params = "lastName")
  public List<Customer> findCustomersByLastName(@RequestParam String lastName) {
    return customerService.findCustomersByLastName(lastName);
  }

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public Customer addCustomer(@RequestBody Customer newCustomer) {
    return customerService.addCustomer(newCustomer);
  }


  @ExceptionHandler(CustomerNotFoundException.class)
  public ResponseEntity<String> handleCustomerNotFoundException(CustomerNotFoundException ex) {
    log.error("Not found", ex);
    return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
  }

}
