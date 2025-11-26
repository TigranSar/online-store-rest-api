package com.online.store.mapper;

import com.online.store.dto.orderItem.OrderItemResponseDto;
import com.online.store.entity.OrderItem;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface OrderItemMapper{
    @Mapping(source = "product.id", target = "productId")
    OrderItemResponseDto toOrderItemDto(OrderItem orderItem);

    List<OrderItemResponseDto> toOrderItemDtoList(List<OrderItem> orderItemList);
}
