package com.belatrixsf.app.web.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.belatrixsf.app.dao.entity.Customer;
import com.belatrixsf.app.service.CustomerService;
import com.belatrixsf.app.web.controller.HelloControllerStandaloneTest.TestConfig;
import java.util.Arrays;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import org.mockito.quality.Strictness;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {TestConfig.class})
@WebAppConfiguration
public class CustomerControllerStandaloneTest {

  @Rule
  public MockitoRule rule = MockitoJUnit.rule().strictness(Strictness.STRICT_STUBS);


  @Mock
  private CustomerService customerService;

  private MockMvc mockMvc;


  @Before
  public void setup() {
    CustomerController customerController = new CustomerController(customerService);
    this.mockMvc = MockMvcBuilders.standaloneSetup(customerController).build();
  }


  @Test
  public void whenCustomersIsRequested_shouldReturnOne() throws Exception {
    Customer customer = new Customer(1L, "Juan", "Loli", "jloli@belatrixsf.com");
    Mockito.when(customerService.findAllCustomers()).thenReturn(Arrays.asList(customer));

    mockMvc.perform(
        get("/customers"))
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$").isArray())
        .andExpect(jsonPath("$", hasSize(1)))
        .andExpect(jsonPath("$[0].id").value(1))
        .andExpect(jsonPath("$[0].firstName").value("Juan"))
        .andExpect(jsonPath("$[0].lastName").value("Loli"))
        .andExpect(jsonPath("$[0].email").value("jloli@belatrixsf.com"));
  }


  @Test
  public void whenCustomersByIdIsRequested_shouldReturnCustomer() throws Exception {
    Customer customer = new Customer(2L, "Juan", "Loli", "jloli@belatrixsf.com");
    Mockito.when(customerService.getCustomerId(2)).thenReturn(customer);

    mockMvc.perform(
        get("/customers/{id}", 2))
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$.id").value(2))
        .andExpect(jsonPath("$.firstName").value("Juan"))
        .andExpect(jsonPath("$.lastName").value("Loli"))
        .andExpect(jsonPath("$.email").value("jloli@belatrixsf.com"));
  }


  @Test
  public void whenCostumerIsPosted_shouldReturnCreated() throws Exception {
    Mockito.when(customerService.saveCustomer(Mockito.any(Customer.class)))
        .thenReturn(new Customer(1L, "Juan", "Loli", "jloli@belatrixsf.com"));

    mockMvc.perform(
        post("/customers")
            .contentType(MediaType.APPLICATION_JSON)
            .content("{\n"
                + "    \"firstName\": \"Juan\",\n"
                + "    \"lastName\": \"Loli\",\n"
                + "    \"email\": \"jloli@belatrixsf.com\"\n"
                + "}"))
        .andDo(print())
        .andExpect(status().isCreated())
        .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
        .andExpect(header().string("Location", "http://localhost/customers/1"))
        .andExpect(jsonPath("$.id").value(1))
        .andExpect(jsonPath("$.firstName").value("Juan"))
        .andExpect(jsonPath("$.lastName").value("Loli"))
        .andExpect(jsonPath("$.email").value("jloli@belatrixsf.com"));

    //Verifies correct argument is sent to service
    ArgumentCaptor<Customer> argumentCaptor = ArgumentCaptor.forClass(Customer.class);
    Mockito.verify(customerService).saveCustomer(argumentCaptor.capture());
    Customer postedCustomer = argumentCaptor.getValue();

    assertThat(postedCustomer).isNotNull();
    assertThat(postedCustomer.getId()).isNull();
    assertThat(postedCustomer.getFirstName()).isEqualTo("Juan");
    assertThat(postedCustomer.getLastName()).isEqualTo("Loli");
    assertThat(postedCustomer.getEmail()).isEqualTo("jloli@belatrixsf.com");
  }

  @Test
  public void whenCostumerInfoIsIncorrect_shouldReturnBadRequest() throws Exception {
    mockMvc.perform(
        post("/customers")
            .contentType(MediaType.APPLICATION_JSON)
            .content("{\n"
                + "    \"lastName\": \"Loli\",\n"
                + "    \"email\": \"jloli@belatrixsf.com\"\n"
                + "}"))
        .andDo(print())
        .andExpect(status().isBadRequest());
  }


  @Configuration
  @EnableWebMvc
  public static class TestConfig implements WebMvcConfigurer {

  }

}
