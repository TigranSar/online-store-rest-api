package com.tigran.store.mapper;

import com.tigran.store.dto.order.OrderResponseDto;
import com.tigran.store.entity.Order;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {OrderItemMapper.class})
public interface OrderMapper {

    @Mapping(source = "customer.id", target = "customerId")
    OrderResponseDto toOrderResponseDto(Order order);
    //Customer must be set in the service using customerId
    Order toOrder(OrderResponseDto orderResponseDto);
}
