package com.belatrixsf.app.web.controller;

import com.belatrixsf.app.dao.entity.Customer;
import com.belatrixsf.app.service.CustomerNotFoundException;
import com.belatrixsf.app.service.CustomerService;
import java.net.URI;
import java.util.List;
import javax.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
// http://localhost:8080/customers
@RequestMapping("/customers")
@Validated
public class CustomerController {

  private static final Logger log = LoggerFactory.getLogger(CustomerController.class);


  private final CustomerService customerService;


  public CustomerController(CustomerService customerService) {
    this.customerService = customerService;
  }

  //http://localhost:8080/customers
  @GetMapping
  public List<Customer> findAllCustomers() {
    return customerService.findAllCustomers();
  }

  //http://localhost:8080/customers?lastName=234
  @GetMapping(params = "lastName")
  public List<Customer> findCustomersByLastName(@RequestParam String lastName) {
    return customerService.findCustomersByLastName(lastName);
  }

  //GET http://localhost:8080/customers/1
  @GetMapping("/{customerId}")
  public Customer getCustomerById(@PathVariable long customerId) {
    return customerService.getCustomerId(customerId);
  }

  //POST http://localhost:8080/customers
  @PostMapping
  public ResponseEntity<Customer> addCustomer(@Valid @RequestBody Customer newCustomer) {
    Customer savedCustomer = customerService.saveCustomer(newCustomer);

    URI location = ServletUriComponentsBuilder.fromCurrentRequest()
        .path("/{id}")
        .buildAndExpand(savedCustomer.getId())
        .toUri();

    return ResponseEntity.created(location).body(savedCustomer);
  }

  //PUT http://localhost:8080/customers/1
  @PutMapping("/{customerId}")
  @ResponseStatus(HttpStatus.OK)
  public void updateCustomer(@PathVariable long customerId,
      @Valid @RequestBody Customer customer) {

    customer.setId(customerId);
    customerService.updateCustomer(customer);
  }

  //DELETE http://localhost:8080/customers/1
  @DeleteMapping("/{customerId}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void deleteCustomerById(@PathVariable long customerId) {
    customerService.deleteCustomerById(customerId);
  }

  @ExceptionHandler(CustomerNotFoundException.class)
  public ResponseEntity<String> handleCustomerNotFoundException(CustomerNotFoundException ex) {
    log.error("Not found", ex);
    return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
  }

}
