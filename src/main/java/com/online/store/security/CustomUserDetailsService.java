package com.online.store.security;

import com.online.store.entity.Account;
import com.online.store.exception.ResourceNotFoundException;
import com.online.store.repository.AccountRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
public class CustomUserDetailsService implements UserDetailsService {
    private final AccountRepository accountRepository;

    public CustomUserDetailsService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Account account = accountRepository
                .findByEmail(username)
                .orElseThrow(() -> new ResourceNotFoundException("Account with this username is not found!"));
        return new CustomUserDetails(account);
    }
}
