package com.example.lugnutsautomotive.web;

import com.example.lugnutsautomotive.common.NotFoundException;
import com.example.lugnutsautomotive.domain.Customer;
import com.example.lugnutsautomotive.domain.CustomerOrder;
import com.example.lugnutsautomotive.domain.OrderDetail;
import com.example.lugnutsautomotive.repository.CustomerOrderRepository;
import com.example.lugnutsautomotive.repository.CustomerRepository;
import com.example.lugnutsautomotive.repository.OrderDetailRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/lugnuts/orders")
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
    public List<CustomerOrder> list(@RequestParam(name = "customerNumber", required = false) Integer customerNumber) {
        if (customerNumber != null) {
            return orderRepository.findByCustomer_CustomerNumber(customerNumber);
        }
        return orderRepository.findAll();
    }

    @GetMapping("/{orderNumber}")
    public CustomerOrder get(@PathVariable Integer orderNumber) {
        return orderRepository.findById(orderNumber)
                .orElseThrow(() -> new NotFoundException("Order " + orderNumber + " not found"));
    }

    @GetMapping("/{orderNumber}/details")
    public List<OrderDetail> getDetails(@PathVariable Integer orderNumber) {
        // ensure order exists for proper 404
        orderRepository.findById(orderNumber)
                .orElseThrow(() -> new NotFoundException("Order " + orderNumber + " not found"));
        return orderDetailRepository.findByOrder_OrderNumber(orderNumber);
    }

    @PostMapping
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
    public CustomerOrder update(@PathVariable Integer orderNumber, @RequestBody CustomerOrder order) {
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
    public void delete(@PathVariable Integer orderNumber) {
        orderRepository.deleteById(orderNumber);
    }
}