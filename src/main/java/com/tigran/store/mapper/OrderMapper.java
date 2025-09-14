package com.tigran.store.mapper;

import com.tigran.store.dto.OrderItemResponseDto;
import com.tigran.store.dto.OrderResponseDto;
import com.tigran.store.entity.Order;
import com.tigran.store.entity.OrderItem;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

import java.util.List;

@Mapper(componentModel = "spring", uses = {OrderItemMapper.class})
public interface OrderMapper {

    @Mapping(source = "customer.id", target = "customerId")
    OrderResponseDto toOrderResponseDto(Order order);
    //Customer must be set in the service using customerId
    Order toOrder(OrderResponseDto orderResponseDto);
}
