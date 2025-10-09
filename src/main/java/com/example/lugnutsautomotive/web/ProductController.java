package com.example.lugnutsautomotive.web;

import com.example.lugnutsautomotive.common.NotFoundException;
import com.example.lugnutsautomotive.domain.Product;
import com.example.lugnutsautomotive.domain.ProductLine;
import com.example.lugnutsautomotive.repository.ProductLineRepository;
import com.example.lugnutsautomotive.repository.ProductRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/lugnuts/products")
public class ProductController {

    private final ProductRepository productRepository;
    private final ProductLineRepository productLineRepository;

    public ProductController(ProductRepository productRepository, ProductLineRepository productLineRepository) {
        this.productRepository = productRepository;
        this.productLineRepository = productLineRepository;
    }

    @GetMapping
    public List<Product> list(@RequestParam(required = false) String line,
                              @RequestParam(required = false, name = "q") String query) {
        if (line != null) {
            ProductLine pl = productLineRepository.findById(line)
                    .orElseThrow(() -> new NotFoundException("ProductLine '" + line + "' not found"));
            return productRepository.findByProductLine(pl);
        }
        if (query != null && !query.isBlank()) {
            return productRepository.findByProductNameContainingIgnoreCase(query);
        }
        return productRepository.findAll();
    }

    @GetMapping("/{code}")
    public Product get(@PathVariable String code) {
        return productRepository.findById(code)
                .orElseThrow(() -> new NotFoundException("Product '" + code + "' not found"));
    }

    @PostMapping
    public Product create(@RequestBody Product product) {
        if (product.getProductLine() != null) {
            String key = product.getProductLine().getProductLine();
            ProductLine pl = productLineRepository.findById(key)
                    .orElseThrow(() -> new NotFoundException("ProductLine '" + key + "' not found"));
            product.setProductLine(pl);
        }
        return productRepository.save(product);
    }

    @PutMapping("/{code}")
    public Product update(@PathVariable String code, @RequestBody Product product) {
        Product existing = productRepository.findById(code)
                .orElseThrow(() -> new NotFoundException("Product '" + code + "' not found"));
        existing.setProductName(product.getProductName());
        if (product.getProductLine() != null) {
            String key = product.getProductLine().getProductLine();
            ProductLine pl = productLineRepository.findById(key)
                    .orElseThrow(() -> new NotFoundException("ProductLine '" + key + "' not found"));
            existing.setProductLine(pl);
        }
        existing.setProductVendor(product.getProductVendor());
        existing.setProductDescription(product.getProductDescription());
        existing.setQuantityInStock(product.getQuantityInStock());
        existing.setBuyPrice(product.getBuyPrice());
        existing.setMsrp(product.getMsrp());
        return productRepository.save(existing);
    }

    @DeleteMapping("/{code}")
    public void delete(@PathVariable String code) {
        productRepository.deleteById(code);
    }
}