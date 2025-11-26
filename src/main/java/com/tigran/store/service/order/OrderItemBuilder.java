package com.tigran.store.service.order;

import com.tigran.store.dto.orderItem.OrderItemRequestDto;
import com.tigran.store.entity.Order;
import com.tigran.store.entity.OrderItem;
import com.tigran.store.entity.Product;
import com.tigran.store.exception.ProductOutOfStockException;
import com.tigran.store.service.ProductService;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Component
public class OrderItemBuilder {
    private final ProductService productService;

    public OrderItemBuilder(ProductService productService) {
        this.productService = productService;
    }

    public List<OrderItem> buildOrderItems(Order order, List<OrderItemRequestDto> orderItemRequestDtos){
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
    private void validateQuantity(int quantity, int quantityInStock){
        if (quantity > quantityInStock){
            throw new ProductOutOfStockException("Not enough stock for this product");
        }
    }
    private BigDecimal calculateOrderItemAmount(BigDecimal price, int quantity){
        return price.multiply(BigDecimal.valueOf(quantity));
    }
}
