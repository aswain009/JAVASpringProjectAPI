package com.example.lugnutsautomotive.web;

import com.example.lugnutsautomotive.common.NotFoundException;
import com.example.lugnutsautomotive.domain.Customer;
import com.example.lugnutsautomotive.domain.CustomerOrder;
import com.example.lugnutsautomotive.domain.OrderDetail;
import com.example.lugnutsautomotive.repository.CustomerOrderRepository;
import com.example.lugnutsautomotive.repository.CustomerRepository;
import com.example.lugnutsautomotive.repository.OrderDetailRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/lugnuts/orders")
@Tag(name = "Orders", description = "Operations on orders and order details")
public class OrderController {

    private final CustomerOrderRepository orderRepository;
    private final CustomerRepository customerRepository;
    private final OrderDetailRepository orderDetailRepository;

    public OrderController(CustomerOrderRepository orderRepository,
                           CustomerRepository customerRepository,
                           OrderDetailRepository orderDetailRepository) {
        this.orderRepository = orderRepository;
        this.customerRepository = customerRepository;
        this.orderDetailRepository = orderDetailRepository;
    }

    @GetMapping
    @Operation(summary = "List orders", description = "List all orders or filter by customer number")
    public List<CustomerOrder> list(@Parameter(description = "Customer number filter")
                                    @RequestParam(name = "customerNumber", required = false) Integer customerNumber) {
        if (customerNumber != null) {
            return orderRepository.findByCustomer_CustomerNumber(customerNumber);
        }
        return orderRepository.findAll();
    }

    @GetMapping("/{orderNumber}")
    @Operation(summary = "Get order by number", responses = {
            @ApiResponse(responseCode = "200", description = "Found", content = @Content(schema = @Schema(implementation = CustomerOrder.class))),
            @ApiResponse(responseCode = "404", description = "Not Found")
    })
    public CustomerOrder get(@Parameter(description = "Order number") @PathVariable Integer orderNumber) {
        return orderRepository.findById(orderNumber)
                .orElseThrow(() -> new NotFoundException("Order " + orderNumber + " not found"));
    }

    @GetMapping("/{orderNumber}/details")
    @Operation(summary = "List order details", responses = {
            @ApiResponse(responseCode = "200", description = "Found",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = OrderDetail.class)))) ,
            @ApiResponse(responseCode = "404", description = "Order Not Found")
    })
    public List<OrderDetail> getDetails(@Parameter(description = "Order number") @PathVariable Integer orderNumber) {
        // ensure order exists for proper 404
        orderRepository.findById(orderNumber)
                .orElseThrow(() -> new NotFoundException("Order " + orderNumber + " not found"));
        return orderDetailRepository.findByOrder_OrderNumber(orderNumber);
    }

    @PostMapping
    @Operation(summary = "Create an order")
    public CustomerOrder create(@RequestBody CustomerOrder order) {
        if (order.getCustomer() != null) {
            Integer id = order.getCustomer().getCustomerNumber();
            Customer customer = customerRepository.findById(id)
                    .orElseThrow(() -> new NotFoundException("Customer " + id + " not found"));
            order.setCustomer(customer);
        }
        return orderRepository.save(order);
    }

    @PutMapping("/{orderNumber}")
    @Operation(summary = "Update an order")
    public CustomerOrder update(@Parameter(description = "Order number") @PathVariable Integer orderNumber, @RequestBody CustomerOrder order) {
        CustomerOrder existing = orderRepository.findById(orderNumber)
                .orElseThrow(() -> new NotFoundException("Order " + orderNumber + " not found"));
        existing.setOrderDate(order.getOrderDate());
        existing.setRequiredDate(order.getRequiredDate());
        existing.setShippedDate(order.getShippedDate());
        existing.setStatus(order.getStatus());
        existing.setComments(order.getComments());
        if (order.getCustomer() != null) {
            Integer id = order.getCustomer().getCustomerNumber();
            Customer customer = customerRepository.findById(id)
                    .orElseThrow(() -> new NotFoundException("Customer " + id + " not found"));
            existing.setCustomer(customer);
        }
        return orderRepository.save(existing);
    }

    @DeleteMapping("/{orderNumber}")
    @Operation(summary = "Delete an order")
    public void delete(@Parameter(description = "Order number") @PathVariable Integer orderNumber) {
        orderRepository.deleteById(orderNumber);
    }
}