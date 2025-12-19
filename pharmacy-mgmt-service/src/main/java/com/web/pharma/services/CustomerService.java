package com.web.pharma.services;

import com.web.pharma.dtos.CustomerDto;

import java.util.List;

public interface CustomerService {
    CustomerDto registerCustomer(CustomerDto dto);
    CustomerDto updateCustomer(Long id, CustomerDto dto);
    CustomerDto getCustomer(Long id);
    List<CustomerDto> getAllCustomers();
}
