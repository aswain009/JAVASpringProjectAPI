package com.example.lugnutsautomotive.web;

import com.example.lugnutsautomotive.common.NotFoundException;
import com.example.lugnutsautomotive.domain.Customer;
import com.example.lugnutsautomotive.repository.CustomerRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/lugnuts/customers")
public class CustomerController {

    private final CustomerRepository customerRepository;

    public CustomerController(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @GetMapping
    public List<Customer> list(@RequestParam(name = "q", required = false) String query) {
        if (query != null && !query.isBlank()) {
            return customerRepository.findByCustomerNameContainingIgnoreCase(query);
        }
        return customerRepository.findAll();
    }

    @GetMapping("/{id}")
    public Customer get(@PathVariable Integer id) {
        return customerRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Customer " + id + " not found"));
    }

    @PostMapping
    public Customer create(@RequestBody Customer c) {
        return customerRepository.save(c);
    }

    @PutMapping("/{id}")
    public Customer update(@PathVariable Integer id, @RequestBody Customer c) {
        Customer existing = customerRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Customer " + id + " not found"));
        existing.setCustomerName(c.getCustomerName());
        existing.setContactFirstName(c.getContactFirstName());
        existing.setContactLastName(c.getContactLastName());
        existing.setPhone(c.getPhone());
        existing.setAddressLine1(c.getAddressLine1());
        existing.setAddressLine2(c.getAddressLine2());
        existing.setCity(c.getCity());
        existing.setState(c.getState());
        existing.setPostalCode(c.getPostalCode());
        existing.setCountry(c.getCountry());
        existing.setSalesRep(c.getSalesRep());
        existing.setCreditLimit(c.getCreditLimit());
        return customerRepository.save(existing);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Integer id) {
        customerRepository.deleteById(id);
    }
}