package com.tigran.store.dto.request;

import com.tigran.store.entity.Category;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
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
    private BigDecimal price;
    @NotNull(message = "Stock quantity is required")
    @PositiveOrZero(message = "Quantity cannot be negative")
    private Integer stockQuantity;
    @NotNull(message = "Category id is required")
    private Long categoryId;
}
