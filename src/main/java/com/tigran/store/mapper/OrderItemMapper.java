package com.tigran.store.mapper;

import com.tigran.store.dto.OrderItemResponseDto;
import com.tigran.store.entity.OrderItem;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface OrderItemMapper{
    @Mapping(source = "product.id", target = "productId")
    OrderItemResponseDto toOrderItemDto(OrderItem orderItem);

    List<OrderItemResponseDto> toOrderItemDtoList(List<OrderItem> orderItemList);
}
