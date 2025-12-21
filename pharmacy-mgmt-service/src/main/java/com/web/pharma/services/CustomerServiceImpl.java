package com.web.pharma.services;

import com.web.pharma.dtos.CustomerDto;
import com.web.pharma.models.Customer;
import com.web.pharma.repos.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository repository;

    @Override
    public CustomerDto registerCustomer(CustomerDto dto) {
        Customer customer = CustomerDto.toEntity(dto);
        Customer saved = repository.save(customer);
        return CustomerDto.toDto(saved);
    }

    @Override
    public CustomerDto updateCustomer(Long id, CustomerDto dto) {
        Customer customer = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Customer not found"));
        customer.setName(dto.name());
        customer.setEmail(dto.email());
        customer.setPhone(dto.phone());
        return CustomerDto.toDto(repository.save(customer));
    }

    @Override
    public CustomerDto getCustomer(Long id) {
        return repository.findById(id)
                .map(CustomerDto::toDto)
                .orElseThrow(() -> new RuntimeException("Customer not found"));
    }

    @Override
    public List<CustomerDto> getAllCustomers() {
        return repository.findAll().stream()
                .map(CustomerDto::toDto)
                .collect(Collectors.toList());
    }
}

