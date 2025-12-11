package com.online.store.service.auth;

import com.online.store.dto.auth.RegistrationDto;
import com.online.store.entity.Account;
import com.online.store.entity.Role;
import com.online.store.service.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AccountBuilder {
    private final RoleService roleService;
    private final PasswordEncoder passwordEncoder;

    public Account build(RegistrationDto dto){
        Account account = new Account();
        Role role = roleService.getByName("ROLE_USER");
        account.setEmail(dto.getEmail());
        account.getRoles().add(role);
        account.setPassword(passwordEncoder.encode(dto.getPassword()));
        return account;
    }
}
