package com.tigran.store.service;

import com.tigran.store.entity.Customer;
import com.tigran.store.exception.ResourceNotFoundException;
import com.tigran.store.repository.CustomerRepository;
import org.springframework.stereotype.Service;

@Service
public class CustomerService {
    private CustomerRepository customerRepository;

    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }
    public Customer getCustomerEntity(Long id){
        Customer customer = customerRepository.findById(id).
                orElseThrow(() -> new ResourceNotFoundException("Customer not found"));
        return customer;
    }
}
