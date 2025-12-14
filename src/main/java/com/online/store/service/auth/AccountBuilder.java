package com.online.store.service.auth;

import com.online.store.dto.auth.RegistrationDto;
import com.online.store.entity.Account;
import com.online.store.entity.status.AccountStatus;
import com.online.store.entity.status.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AccountBuilder {
    private final PasswordEncoder passwordEncoder;

    public Account build(RegistrationDto dto){
        Account account = new Account();
        account.getRoles().add(Role.ROLE_USER);
        account.setEmail(dto.getEmail());
        account.setStatus(AccountStatus.ACTIVE);
        account.setPassword(passwordEncoder.encode(dto.getPassword()));
        return account;
    }
}
