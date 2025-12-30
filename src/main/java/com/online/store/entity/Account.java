package com.online.store.entity;

import com.online.store.entity.status.AccountStatus;
import com.online.store.entity.status.Role;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "accounts")
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String email;
    private String password;
    @Column(nullable = false, updatable = false)
    private LocalDateTime joinedAt;
    @Enumerated(EnumType.STRING)
    private AccountStatus status;
    @OneToOne(mappedBy = "account")
    private Customer customer;
    private List<Role> roles = new ArrayList<>();
}
