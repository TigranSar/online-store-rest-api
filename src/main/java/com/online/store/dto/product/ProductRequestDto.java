package com.online.store.dto.product;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProductRequestDto {
    @NotBlank(message = "Name cannot be empty")
    private String name;
    @NotBlank(message = "Description  cannot be empty")
    private String description;
    @NotNull(message = "Price is required")
    @PositiveOrZero(message = "The price cannot be negative")
    @Digits(integer = 10, fraction = 2)
    private BigDecimal price;
    @NotNull(message = "Stock quantity is required")
    @PositiveOrZero(message = "Quantity cannot be negative")
    private Integer stockQuantity;
    @NotNull(message = "Category id is required")
    private Long categoryId;
}
