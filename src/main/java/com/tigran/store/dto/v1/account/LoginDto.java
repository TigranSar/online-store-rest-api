package com.tigran.store.dto.v1.account;

import jakarta.validation.constraints.Email;
import lombok.Data;

@Data
public class LoginDto {
    @Email
    private String email;
    private String password;
}
