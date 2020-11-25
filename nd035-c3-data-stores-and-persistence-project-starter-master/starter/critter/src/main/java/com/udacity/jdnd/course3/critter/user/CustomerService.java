package com.udacity.jdnd.course3.critter.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class CustomerService {
    @Autowired
    CustomerRepository customerRepository;
    public Customer save(Customer customer) {
        return customerRepository.save(customer);
    }
    public List<Customer> findAllCustomers() {
        return customerRepository.findAll();
    }
    public Customer findCustomerById(Long id) {
        return customerRepository.findById(id).orElse(null);
    }
}
