package com.online.store.dto.orderItem;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderItemRequestDto {
    @NotNull(message = "Product id cannot be null")
    private Long productId;
    @Min(value = 1, message = "Product quantity cannot be less than 1")
    private int quantity;
}

