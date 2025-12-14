package com.online.store.service.auth;

import com.online.store.dto.auth.RegistrationDto;
import com.online.store.entity.Account;
import com.online.store.entity.Customer;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
public class CustomerBuilder {
    public Customer build(RegistrationDto dto){
        Customer customer = new Customer();
        customer.setName(dto.getName());
        customer.setAddress(dto.getAddress());
        customer.setPhone(dto.getPhoneNumber());
        return customer;
    }
}
