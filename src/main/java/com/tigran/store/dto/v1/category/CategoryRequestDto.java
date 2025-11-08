package com.tigran.store.dto.v1.category;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CategoryRequestDto {
    @NotBlank(message = "Name cannot be empty")
    @Pattern(regexp = "^[A-Za-zА-Яа-яЁё\\s]+$", message = "Name must contain only letters")
    private String name;
}
