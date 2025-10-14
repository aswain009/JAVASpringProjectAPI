package com.example.lugnutsautomotive.web;

import com.example.lugnutsautomotive.common.NotFoundException;
import com.example.lugnutsautomotive.domain.Customer;
import com.example.lugnutsautomotive.repository.CustomerRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/lugnuts/customers")
@Tag(name = "Customers", description = "Operations on customers")
public class CustomerController {

    private final CustomerRepository customerRepository;

    public CustomerController(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @GetMapping
    @Operation(summary = "List customers", description = "Returns all customers or filters by name with query parameter q.")
    public List<Customer> list(@Parameter(description = "Case-insensitive name filter")
                               @RequestParam(name = "q", required = false) String query) {
        if (query != null && !query.isBlank()) {
            return customerRepository.findByCustomerNameContainingIgnoreCase(query);
        }
        return customerRepository.findAll();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get customer by id", responses = {
            @ApiResponse(responseCode = "200", description = "Found", content = @Content(schema = @Schema(implementation = Customer.class))),
            @ApiResponse(responseCode = "404", description = "Not Found")
    })
    public Customer get(@Parameter(description = "Customer number") @PathVariable Integer id) {
        return customerRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Customer " + id + " not found"));
    }

    @PostMapping
    @Operation(summary = "Create a customer")
    public Customer create(@RequestBody Customer c) {
        return customerRepository.save(c);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update a customer")
    public Customer update(@Parameter(description = "Customer number") @PathVariable Integer id, @RequestBody Customer c) {
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
    @Operation(summary = "Delete a customer")
    public void delete(@Parameter(description = "Customer number") @PathVariable Integer id) {
        customerRepository.deleteById(id);
    }
}