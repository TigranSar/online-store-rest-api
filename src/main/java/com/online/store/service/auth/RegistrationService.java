package com.online.store.service.auth;

import com.online.store.dto.auth.RegistrationDto;
import com.online.store.dto.auth.RegistrationResponseDto;
import com.online.store.entity.Account;
import com.online.store.entity.Customer;
import com.online.store.repository.CustomerRepository;
import com.online.store.security.CustomUserDetails;
import com.online.store.security.JwtUtilsService;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;


@Service
public class RegistrationService {
    private final CheckUniqueness checkUniqueness;
    private final AccountBuilder accountBuilder;
    private final CustomerBuilder customerBuilder;
    private final JwtUtilsService jwtUtilsService;
    private final CustomerRepository customerRepository;
    public RegistrationService(CheckUniqueness checkUniqueness,
                               AccountBuilder accountBuilder,
                               CustomerBuilder customerBuilder,
                               JwtUtilsService jwtUtilsService,
                               CustomerRepository customerRepository) {
        this.checkUniqueness = checkUniqueness;
        this.accountBuilder = accountBuilder;
        this.customerBuilder = customerBuilder;
        this.jwtUtilsService = jwtUtilsService;
        this.customerRepository = customerRepository;
    }

    @Transactional
    public RegistrationResponseDto register(RegistrationDto registrationDto){
        checkUniqueness.checkUniqueness(registrationDto); // checking uniqueness of email and phone number
        Account account = accountBuilder.build(registrationDto);
        Customer customer = customerBuilder.build(registrationDto);
        customer.setAccount(account);
        customerRepository.save(customer);
        String token = jwtUtilsService.generateToken(new CustomUserDetails(account));
        return new RegistrationResponseDto(token);
    }
}
