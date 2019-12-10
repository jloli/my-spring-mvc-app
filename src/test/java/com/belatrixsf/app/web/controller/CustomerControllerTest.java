package com.belatrixsf.app.web.controller;

import static org.assertj.core.api.Assertions.assertThat;

import com.belatrixsf.app.dao.entity.Customer;
import com.belatrixsf.app.service.CustomerNotFoundException;
import com.belatrixsf.app.service.CustomerService;
import java.util.Arrays;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.StrictStubs.class)
public class CustomerControllerTest {

  @Mock
  private CustomerService customerService;

  private CustomerController customerController;


  @Before
  public void setup() {
    customerController = new CustomerController(customerService);
  }

  @Test
  public void whenGetCustomers_shouldReturnListWithOne() {
    Customer customer = new Customer(1L, "Juan", "Loli", "jloli@belatrixsf.com");
    Mockito.when(customerService.findAllCustomers())
        .thenReturn(Arrays.asList(customer));

    List<Customer> customers = customerController.findAllCustomers();

    assertThat(customers).isNotNull();
    assertThat(customers).hasSize(1);
    assertThat(customers.get(0).getId()).isEqualTo(1L);
    assertThat(customers.get(0).getFirstName()).isEqualTo("Juan");
    assertThat(customers.get(0).getLastName()).isEqualTo("Loli");
    assertThat(customers.get(0).getEmail()).isEqualTo("jloli@belatrixsf.com");
  }

  @Test
  public void whenGetCustomerById_shouldReturnCustomer() {
    Mockito.when(customerService.getCustomerId(1L))
        .thenReturn(new Customer(1L, "Juan", "Loli", "jloli@belatrixsf.com"));

    Customer customer = customerController.getCustomerById(1L);

    assertThat(customer).isNotNull();
    assertThat(customer.getId()).isEqualTo(1L);
    assertThat(customer.getFirstName()).isEqualTo("Juan");
    assertThat(customer.getLastName()).isEqualTo("Loli");
    assertThat(customer.getEmail()).isEqualTo("jloli@belatrixsf.com");
  }

  @Test(expected = CustomerNotFoundException.class)
  public void whenCustomerDoesNotExist_shouldThrowException() {
    Mockito.when(customerService.getCustomerId(2L))
        .thenThrow(CustomerNotFoundException.class);

    customerController.getCustomerById(2L);
  }


}
