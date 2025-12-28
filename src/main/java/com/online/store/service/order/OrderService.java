package com.online.store.service.order;

import com.online.store.dto.order.OrderResponseDto;
import com.online.store.dto.order.OrderRequestDto;
import com.online.store.entity.Order;
import com.online.store.entity.OrderItem;
import com.online.store.entity.status.OrderStatus;
import com.online.store.exception.OrderCannotBeCancelledException;
import com.online.store.exception.ResourceNotFoundException;
import com.online.store.mapper.OrderMapper;
import com.online.store.repository.OrderRepository;
import com.online.store.security.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final OrderBuilder orderBuilder;
    private final OrderItemBuilder orderItemBuilder;
    private final OrderMapper orderMapper;
    private final OrderStatusValidation orderStatusValidation;


    // ===================== CREATE ORDER =====================
    @PreAuthorize("hasRole('USER')")
    @Transactional
    public OrderResponseDto createOrder(OrderRequestDto orderRequestDto){
        Order order = orderBuilder.buildOrderByAccountId(getCurrentUserId());
        List<OrderItem> orderItems = orderItemBuilder.buildOrderItems(order,orderRequestDto.getOrderItems());
        BigDecimal orderTotalPrice = calculateOrderTotalPrice(orderItems);
        order.setOrderItems(orderItems);
        order.setTotalPrice(orderTotalPrice);
        return orderMapper.toOrderResponseDto(orderRepository.save(order));
    }

    // ===================== CANCEL ORDER =====================
    @PreAuthorize("hasRole('USER')")
    @Transactional
    public OrderResponseDto cancelOrder(Long id){
        Order order = orderRepository.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException("Order not found"));
        isOrderOwner(order); //checking if order owned by current user (User can cancel only own orders)
        orderStatusValidation.validate(order,OrderStatus.CANCELLED); //Check status transition
        order.setStatus(OrderStatus.CANCELLED);
        return orderMapper.toOrderResponseDto(order);
    }

    // ===================== SET STATUS =====================
    @PreAuthorize("hasRole('ADMIN')")
    @Transactional
    public OrderResponseDto setOrderStatus(Long id, OrderStatus status){
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found"));
        orderStatusValidation.validate(order,status);
        order.setStatus(status);
        return orderMapper.toOrderResponseDto(order);
    }

    // ===================== VIEW ALL ORDERS =====================
    @Transactional(readOnly = true)
    @PreAuthorize("hasRole('ADMIN')")
    public Page<OrderResponseDto> getAllOrders(OrderStatus orderStatus,
                                               BigDecimal minPrice,
                                               BigDecimal maxPrice,
                                               Pageable pageable){
        Specification<Order> spec = Specification.allOf(
                OrderSpecification.hasStatus(orderStatus),
                OrderSpecification.priceFrom(minPrice),
                OrderSpecification.priceTo(maxPrice)
        );
        return orderRepository.findAll(spec,pageable).map(orderMapper::toOrderResponseDto);
    }


    // ===================== CURRENT CUSTOMER ORDERS =====================
    @Transactional(readOnly = true)
    @PreAuthorize("hasRole('USER')")
    public Page<OrderResponseDto> getOwnOrders(OrderStatus status, Pageable pageable){
        if (status != null){
            return orderRepository.findOrdersByCustomerAccountIdAndStatus(getCurrentUserId(),status,pageable)
                    .map(orderMapper::toOrderResponseDto);
        }
        return orderRepository.findOrdersByCustomerAccountId(getCurrentUserId(),pageable)
                .map(orderMapper::toOrderResponseDto);
    }






    private BigDecimal calculateOrderTotalPrice(List<OrderItem> items){
        return items.stream()
                .map(OrderItem::getTotalPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
    private Long getCurrentUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        return userDetails.currentUserId();
    }

    private void isOrderOwner(Order order){
        if (!order.getCustomer().getAccount().getId().equals(getCurrentUserId())){
            throw new OrderCannotBeCancelledException("You cannot cancel someone else's order");
        }
    }
}


