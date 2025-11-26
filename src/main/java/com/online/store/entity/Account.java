package com.online.store.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "accounts")
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String email;
    private String password;
    @OneToOne()
    @JoinColumn(name = "customer_id")
    private Customer customer;
    @OneToMany(mappedBy = "account", fetch = FetchType.EAGER)
    private List<RoleAccount> roles = new ArrayList<>();
}
