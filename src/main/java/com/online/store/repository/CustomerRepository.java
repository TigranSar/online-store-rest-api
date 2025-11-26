package com.online.store.repository;

import com.online.store.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {

    boolean existsCustomerByPhone(String phone);
    boolean existsCustomerByEmail(String email);
}
