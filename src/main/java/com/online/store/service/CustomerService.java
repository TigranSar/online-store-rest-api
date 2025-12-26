package com.online.store.service;

import com.online.store.dto.customer.CustomerResponseDto;
import com.online.store.dto.customer.CustomerRequestDto;
import com.online.store.entity.Customer;
import com.online.store.exception.NonUniqueDataException;
import com.online.store.exception.ResourceNotFoundException;
import com.online.store.mapper.CustomerMapper;
import com.online.store.repository.CustomerRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CustomerService {
    private CustomerRepository customerRepository;
    private CustomerMapper customerMapper;

    public CustomerService(CustomerRepository customerRepository,
                           CustomerMapper customerMapper) {
        this.customerRepository = customerRepository;
        this.customerMapper = customerMapper;
    }
    public CustomerResponseDto getCustomerByEmail(String email){
        Customer customer = customerRepository.findByEmail(email)
                .orElseThrow(()-> new ResourceNotFoundException("Customer not found"));
        return customerMapper.toCustomerDto(customer);
    }
}
