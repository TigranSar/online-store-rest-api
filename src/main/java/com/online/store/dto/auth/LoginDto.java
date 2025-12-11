package com.online.store.dto.auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginDto {
    @NotBlank(message = "Required Email")
    @Email(message = "Enter correct email")
    private String email;

    @NotBlank(message = "Required Password")
    @Size(min = 6, max = 64, message = "Password should contain 6-64 symbols")
    @Pattern(
            regexp = "^(?=.*[0-9])(?=.*[a-zA-Z]).{6,64}$",
            message = "The password must contain at least one number and one letter."
    )
    private String password;
}
