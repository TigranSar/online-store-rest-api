package com.online.store.dto.order;

import com.online.store.dto.orderItem.OrderItemRequestDto;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrderRequestDto {
    @NotNull(message = "Order items cannot be null")
    @Size(min = 1, message = "You should choose at least 1 item for ordering")
    @Valid
    private List<OrderItemRequestDto> orderItems;
}
