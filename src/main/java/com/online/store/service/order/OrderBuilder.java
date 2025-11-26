package com.online.store.service.order;

import com.online.store.entity.Customer;
import com.online.store.entity.Order;
import com.online.store.entity.OrderStatus;
import com.online.store.service.CustomerService;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class OrderBuilder {
    private final CustomerService customerService;

    public OrderBuilder(CustomerService customerService) {
        this.customerService = customerService;
    }

    public Order buildOrder(Long customerId){
        Order order = new Order();
        Customer customer = customerService.getCustomerEntity(customerId);
        order.setCustomer(customer);
        order.setStatus(OrderStatus.NEW);
        order.setCreatedAt(LocalDateTime.now());
        order.setUpdatedAt(LocalDateTime.now());
        return order;
    }
}
