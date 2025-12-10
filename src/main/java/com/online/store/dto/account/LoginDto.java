package com.online.store.dto.account;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class LoginDto {
    @NotBlank(message = "Required Email")
    @Email(message = "Enter correct email")
    private String email;

    @NotBlank(message = "Required Password")
    @Size(min = 6, max = 64, message = "Password should be between 6 and 64 symbols")
    @Pattern(
            regexp = "^(?=.*[0-9])(?=.*[a-zA-Z]).{6,64}$",
            message = "The password must contain at least one number and one letter."
    )
    private String password;
}
