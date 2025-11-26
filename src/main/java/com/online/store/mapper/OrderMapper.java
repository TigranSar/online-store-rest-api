package com.online.store.mapper;

import com.online.store.dto.order.OrderResponseDto;
import com.online.store.entity.Order;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {OrderItemMapper.class})
public interface OrderMapper {

    @Mapping(source = "customer.id", target = "customerId")
    OrderResponseDto toOrderResponseDto(Order order);
    //Customer must be set in the service using customerId
    Order toOrder(OrderResponseDto orderResponseDto);
}
