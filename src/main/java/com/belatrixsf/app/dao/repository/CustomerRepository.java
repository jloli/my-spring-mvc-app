package com.belatrixsf.app.dao.repository;

import com.belatrixsf.app.dao.entity.Customer;
import java.util.List;
import org.springframework.data.repository.CrudRepository;

public interface CustomerRepository extends CrudRepository<Customer, Long> {

  List<Customer> findByLastName(String lastName);

  Customer findById(long id);

}
