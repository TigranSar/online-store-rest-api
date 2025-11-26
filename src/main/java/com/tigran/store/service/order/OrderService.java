package com.tigran.store.service.order;

import com.tigran.store.dto.order.OrderResponseDto;
import com.tigran.store.dto.order.OrderRequestDto;
import com.tigran.store.entity.*;
import com.tigran.store.mapper.OrderMapper;
import com.tigran.store.repository.OrderRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
public class OrderService {
    private final OrderRepository orderRepository;
    private final OrderBuilder orderBuilder;
    private final OrderItemBuilder orderItemBuilder;
    private final OrderMapper orderMapper;

    public OrderService(OrderRepository orderRepository,
                        OrderBuilder orderBuilder,
                        OrderItemBuilder orderItemBuilder,
                        OrderMapper orderMapper) {
        this.orderRepository = orderRepository;
        this.orderBuilder = orderBuilder;
        this.orderItemBuilder = orderItemBuilder;
        this.orderMapper = orderMapper;
    }
    @Transactional
    public OrderResponseDto createOrder(OrderRequestDto orderRequestDto){
        Order order = orderBuilder.buildOrder(orderRequestDto.getCustomerId());
        List<OrderItem> orderItems = orderItemBuilder.buildOrderItems(order,orderRequestDto.getOrderItems());
        BigDecimal orderTotalPrice = calculateOrderTotalPrice(orderItems);
        order.setOrderItems(orderItems);
        order.setTotalPrice(orderTotalPrice);
        return orderMapper.toOrderResponseDto(orderRepository.save(order));
    }

    private BigDecimal calculateOrderTotalPrice(List<OrderItem> items){
        BigDecimal totalResult = BigDecimal.ZERO;
        for(OrderItem orderItems : items){
            totalResult = totalResult.add(orderItems.getTotalPrice());
        }
        return totalResult;
    }
}


