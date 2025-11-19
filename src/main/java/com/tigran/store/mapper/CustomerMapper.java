package com.tigran.store.mapper;

import com.tigran.store.dto.customer.CustomerRequestDto;
import com.tigran.store.dto.customer.CustomerResponseDto;
import com.tigran.store.entity.Customer;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {OrderMapper.class})
public interface CustomerMapper {
    CustomerResponseDto toCustomerDto(Customer customer);
    Customer toCustomer(CustomerRequestDto customerRequestDto);
}
