package com.online.store.config;


import com.online.store.entity.Role;
import com.online.store.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DataInitializer {
    private final RoleRepository roleRepository;

    @EventListener(ApplicationReadyEvent.class)
    public void init() {
        if (roleRepository.count() == 0) {
            roleRepository.save(new Role("ROLE_USER"));
            roleRepository.save(new Role("ROLE_ADMIN"));
            System.out.println(">>> Default roles inserted.");
        } else {
            System.out.println(">>> Roles already exist. No insert.");
        }
    }
}
