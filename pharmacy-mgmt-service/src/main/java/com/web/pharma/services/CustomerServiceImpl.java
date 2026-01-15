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

    private final CustomerRepository customerRepository;

    @Override
    public CustomerDto registerCustomer(CustomerDto dto) {
        Customer customer = CustomerDto.toEntity(dto);
        Customer saved = customerRepository.save(customer);
        return CustomerDto.toDto(saved);
    }

    @Override
    public CustomerDto updateCustomer(Long id, CustomerDto dto) {
        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Customer not found"));
        customer.setName(dto.name());
        customer.setEmail(dto.email());
        customer.setPhone(dto.phone());
        return CustomerDto.toDto(customerRepository.save(customer));
    }

    @Override
    public CustomerDto getCustomer(Long id) {
        return customerRepository.findById(id)
                .map(CustomerDto::toDto)
                .orElseThrow(() -> new RuntimeException("Customer not found"));
    }

    @Override
    public List<CustomerDto> getAllCustomers() {
        return customerRepository.findAll().stream()
                .map(CustomerDto::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<CustomerDto> searchCustomersByName(String name) {
        if (name == null || name.trim().length() < 3) {
            throw new IllegalArgumentException("Search keyword must be at least 3 characters");
        }
        return customerRepository
                .findByNameContainingIgnoreCase(name.trim())
                .stream()
                .map(CustomerDto::toDto)
                .toList();
    }

    @Override
    public List<CustomerDto> searchCustomersByPhone(String phone) {
        if (phone == null || phone.trim().length() < 3) {
            throw new IllegalArgumentException("Search keyword must be at least 3 characters");
        }
        return customerRepository
                .findByPhoneContaining(phone.trim())
                .stream()
                .map(CustomerDto::toDto)
                .toList();
    }

    public Long getCustomerCount() {
        return customerRepository.count();
    }

}

