package com.online.store.service.auth;

import com.online.store.dto.auth.RegistrationDto;
import com.online.store.exception.NonUniqueDataException;
import com.online.store.repository.CustomerRepository;
import org.springframework.stereotype.Component;

@Component
public class CheckUniqueness {
    private final CustomerRepository customerRepository;

    public CheckUniqueness(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }
    public void checkUniqueness(RegistrationDto dto){
        if (!isUniqueEmail(dto.getEmail())){
            throw new NonUniqueDataException("User with this email is already exists");
        }else if (!isUniquePhone(dto.getPhoneNumber())){
            throw new NonUniqueDataException("User with this phone number is already exists");
        }
    }

    private boolean isUniqueEmail(String email) {
        return !customerRepository.existsCustomerByEmail(email);
    }

    private boolean isUniquePhone(String phone) {
        return !customerRepository.existsCustomerByPhone(phone);
    }
}
