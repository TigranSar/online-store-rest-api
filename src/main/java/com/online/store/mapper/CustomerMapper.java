package com.online.store.mapper;

import com.online.store.dto.customer.CustomerRequestDto;
import com.online.store.dto.customer.CustomerResponseDto;
import com.online.store.entity.Customer;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {OrderMapper.class})
public interface CustomerMapper {
    CustomerResponseDto toCustomerDto(Customer customer);
    Customer toCustomer(CustomerRequestDto customerRequestDto);
}
