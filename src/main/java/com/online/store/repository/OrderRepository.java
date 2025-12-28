package com.online.store.repository;

import com.online.store.entity.Order;
import com.online.store.entity.status.OrderStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<Order,Long>, JpaSpecificationExecutor<Order> {
    @Query("SELECT o FROM Order o WHERE o.customer.account.id = :accountId")
    Page<Order> findOrdersByCustomerAccountId(@Param("accountId")Long id, Pageable pageable);
    Page<Order> findOrderByStatus(OrderStatus status, Pageable pageable);
}
