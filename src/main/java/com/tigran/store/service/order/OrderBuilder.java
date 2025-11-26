package com.tigran.store.service.order;

import com.tigran.store.entity.Customer;
import com.tigran.store.entity.Order;
import com.tigran.store.entity.OrderStatus;
import com.tigran.store.service.CustomerService;
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
