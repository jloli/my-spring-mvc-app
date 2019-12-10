package com.belatrixsf.app.web.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.belatrixsf.app.config.ServicesConfig;
import com.belatrixsf.app.config.WebAppConfig;
import javax.servlet.ServletContext;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockServletContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {ServicesConfig.class, WebAppConfig.class})
@WebAppConfiguration
public class CustomerControllerIntegrationTest {

  @Autowired
  private WebApplicationContext wac;

  private MockMvc mockMvc;


  @Before
  public void setup() throws Exception {
    this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
  }

  @Test
  public void testContext() {
    ServletContext context = wac.getServletContext();
    assertThat(context).isInstanceOf(MockServletContext.class);
    assertThat(wac.getBean(CustomerController.class)).isNotNull();
  }


  @Test
  public void whenCostumerIsPosted_shouldReturnCreated() throws Exception {
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
  }

  @Test
  public void whenCustomersByIdIsRequested_shouldReturnCustomer() throws Exception {
    mockMvc.perform(
        post("/customers")
            .contentType(MediaType.APPLICATION_JSON)
            .content("{\n"
                + "    \"firstName\": \"Juan\",\n"
                + "    \"lastName\": \"Loli\",\n"
                + "    \"email\": \"jloli@belatrixsf.com\"\n"
                + "}"));

    mockMvc.perform(
        get("/customers/{id}", 1))
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$.id").value(1))
        .andExpect(jsonPath("$.firstName").value("Juan"))
        .andExpect(jsonPath("$.lastName").value("Loli"))
        .andExpect(jsonPath("$.email").value("jloli@belatrixsf.com"));
  }

}
