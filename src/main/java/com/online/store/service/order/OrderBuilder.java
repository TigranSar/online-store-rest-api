package com.online.store.service.order;

import com.online.store.entity.Customer;
import com.online.store.entity.Order;
import com.online.store.entity.status.OrderStatus;
import com.online.store.exception.ResourceNotFoundException;
import com.online.store.repository.CustomerRepository;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class OrderBuilder {
    private final CustomerRepository customerRepository;

    public OrderBuilder(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public Order buildOrderByAccountId(Long customerId){
        Order order = new Order();
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(()-> new ResourceNotFoundException("Customer not found"));
        order.setCustomer(customer);
        order.setStatus(OrderStatus.NEW);
        order.setCreatedAt(LocalDateTime.now());
        order.setUpdatedAt(LocalDateTime.now());
        return order;
    }
}
