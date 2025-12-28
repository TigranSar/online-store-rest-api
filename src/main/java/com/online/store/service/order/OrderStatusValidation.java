package com.online.store.service.order;

import com.online.store.entity.Order;
import com.online.store.entity.status.OrderStatus;
import com.online.store.exception.OrderStatusChangeNotAllowedException;
import org.springframework.stereotype.Service;

@Service
public class OrderStatusValidation {
    public void validate(Order order, OrderStatus newStatus){
        if (order.getStatus() == OrderStatus.NEW && newStatus == OrderStatus.CONFIRMED) return;
        if (order.getStatus() == OrderStatus.CONFIRMED && newStatus == OrderStatus.COMPLETED) return;
        if (order.getStatus() == OrderStatus.NEW && newStatus == OrderStatus.CANCELLED) return;

        throw new OrderStatusChangeNotAllowedException(
                "Invalid status transition: " + order.getStatus() + " -> " + newStatus
        );
    }
}
