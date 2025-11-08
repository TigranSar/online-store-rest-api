package com.tigran.store.service;

import com.tigran.store.dto.v1.customer.CustomerResponseDto;
import com.tigran.store.dto.v1.customer.CustomerRequestDto;
import com.tigran.store.entity.Customer;
import com.tigran.store.exception.NonUniqueDataException;
import com.tigran.store.exception.ResourceNotFoundException;
import com.tigran.store.mapper.CustomerMapper;
import com.tigran.store.repository.CustomerRepository;
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
    public Customer getCustomerEntity(Long id){
        Customer customer = customerRepository.findById(id).
                orElseThrow(() -> new ResourceNotFoundException("Customer not found"));
        return customer;
    }
    public CustomerResponseDto getCustomer(Long id){
        return customerMapper.toCustomerDto(getCustomerEntity(id));
    }

    @Transactional
    public CustomerResponseDto createCustomer(CustomerRequestDto customerRequest){
        checkUniqueEmail(customerRequest.getEmail());
        checkUniquePhone(customerRequest.getPhone());
        Customer customer = customerMapper.toCustomer(customerRequest);
        return customerMapper.toCustomerDto(customerRepository.save(customer));
    }

    private void checkUniqueEmail(String email){
        if(customerRepository.existsCustomerByEmail(email)){
            throw new NonUniqueDataException("Customer with this email is present");
        }
    }
    private void checkUniquePhone(String phone){
        if (customerRepository.existsCustomerByPhone(phone)){
            throw new NonUniqueDataException("Customer with this phone number is present");
        }
    }
}
