package com.example.lugnutsautomotive.web;

import com.example.lugnutsautomotive.common.NotFoundException;
import com.example.lugnutsautomotive.domain.Product;
import com.example.lugnutsautomotive.domain.ProductLine;
import com.example.lugnutsautomotive.repository.ProductLineRepository;
import com.example.lugnutsautomotive.repository.ProductRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/lugnuts/products")
@Tag(name = "Products", description = "Operations on products")
public class ProductController {

    private final ProductRepository productRepository;
    private final ProductLineRepository productLineRepository;

    public ProductController(ProductRepository productRepository, ProductLineRepository productLineRepository) {
        this.productRepository = productRepository;
        this.productLineRepository = productLineRepository;
    }

    @GetMapping
    @Operation(summary = "List products", description = "List all products, or filter by product line or search query.")
    public List<Product> list(
            @Parameter(description = "Product line key (e.g., 'Classic Cars')")
            @RequestParam(required = false) String line,
            @Parameter(description = "Case-insensitive contains search on product name")
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
    @Operation(summary = "Get product by code", responses = {
            @ApiResponse(responseCode = "200", description = "Found", content = @Content(schema = @Schema(implementation = Product.class))),
            @ApiResponse(responseCode = "404", description = "Not Found")
    })
    public Product get(@Parameter(description = "Product code") @PathVariable String code) {
        return productRepository.findById(code)
                .orElseThrow(() -> new NotFoundException("Product '" + code + "' not found"));
    }

    @PostMapping
    @Operation(summary = "Create a product")
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
    @Operation(summary = "Update a product")
    public Product update(@Parameter(description = "Product code") @PathVariable String code, @RequestBody Product product) {
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
    @Operation(summary = "Delete a product")
    public void delete(@Parameter(description = "Product code") @PathVariable String code) {
        productRepository.deleteById(code);
    }
}