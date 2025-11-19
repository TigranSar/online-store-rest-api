package com.tigran.store.service;

import com.tigran.store.dto.order.OrderResponseDto;
import com.tigran.store.dto.orderItem.OrderItemRequestDto;
import com.tigran.store.dto.order.OrderRequestDto;
import com.tigran.store.entity.*;
import com.tigran.store.exception.ProductOutOfStockException;
import com.tigran.store.mapper.OrderMapper;
import com.tigran.store.repository.OrderRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class OrderService {
    private OrderRepository orderRepository;
    private CustomerService customerService;
    private ProductService productService;
    private OrderMapper orderMapper;

    public OrderService(OrderRepository orderRepository,
                        CustomerService customerService,
                        ProductService productService,
                        OrderMapper orderMapper) {
        this.orderRepository = orderRepository;
        this.customerService = customerService;
        this.productService = productService;
        this.orderMapper = orderMapper;
    }
    @Transactional
    public OrderResponseDto createOrder(OrderRequestDto orderRequestDto){
        Order order = buildOrder(orderRequestDto.getCustomerId());
        List<OrderItem> orderItems = buildOrderItems(order,orderRequestDto.getOrderItems());
        BigDecimal orderTotalPrice = calculateOrderTotalPrice(orderItems);
        order.setOrderItems(orderItems);
        order.setTotalPrice(orderTotalPrice);
        return orderMapper.toOrderResponseDto(orderRepository.save(order));
    }

    private BigDecimal calculateOrderItemAmount(BigDecimal price, int quantity){
        return price.multiply(BigDecimal.valueOf(quantity));
    }

    private void validateQuantity(int quantity, int quantityInStock){
        if (quantity > quantityInStock){
            throw new ProductOutOfStockException("Not enough stock for this product");
        }
    }

    private List<OrderItem> buildOrderItems(Order order, List<OrderItemRequestDto> orderItemRequestDtos){
        List<OrderItem> orderItemsResult = new ArrayList<>();
        for (OrderItemRequestDto orderItems : orderItemRequestDtos){
            OrderItem orderItem = new OrderItem();
            Product product = productService.getProductEntity(orderItems.getProductId());
            int quantity = orderItems.getQuantity();
            //if quantity is less than in stock, it reduces stock by quantity
            validateQuantity(quantity, product.getStockQuantity());
            product.setStockQuantity(product.getStockQuantity() - quantity);
            // OrderItem total price
            BigDecimal productPrice = product.getPrice();
            BigDecimal orderItemTotalPrice = calculateOrderItemAmount(productPrice, quantity);
            orderItem.setPrice(productPrice);
            orderItem.setQuantity(quantity);
            orderItem.setTotalPrice(orderItemTotalPrice);
            orderItem.setProduct(product);
            orderItem.setOrder(order);
            orderItemsResult.add(orderItem);
        }
        return orderItemsResult;
    }

    private Order buildOrder(Long customerId){
        Order order = new Order();
        Customer customer = customerService.getCustomerEntity(customerId);
        order.setCustomer(customer);
        order.setUpdatedAt(LocalDateTime.now());
        order.setCreatedAt(LocalDateTime.now());
        order.setStatus(OrderStatus.NEW);
        return order;
    }

    public BigDecimal calculateOrderTotalPrice(List<OrderItem> items){
        BigDecimal totalResult = BigDecimal.ZERO;
        for(OrderItem orderItems : items){
            totalResult = totalResult.add(orderItems.getTotalPrice());
        }
        return totalResult;
    }
}


