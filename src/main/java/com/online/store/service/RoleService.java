package com.online.store.service;

import com.online.store.entity.Role;
import com.online.store.exception.ResourceNotFoundException;
import com.online.store.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RoleService {
    private final RoleRepository roleRepository;

    public Role getByName(String name){
        return roleRepository.findByName(name)
                .orElseThrow(()->new ResourceNotFoundException("Role not found"));
    }
}
