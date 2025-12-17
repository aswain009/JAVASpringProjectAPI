package com.example.lugnutsautomotive.web;

import com.example.lugnutsautomotive.common.NotFoundException;
import com.example.lugnutsautomotive.domain.Testimonial;
import com.example.lugnutsautomotive.repository.TestimonialRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/lugnuts/testimonials")
@Tag(name = "Testimonials", description = "CRUD operations for customer testimonials")
public class TestimonialController {

    private final TestimonialRepository testimonialRepository;

    public TestimonialController(TestimonialRepository testimonialRepository) {
        this.testimonialRepository = testimonialRepository;
    }

    @GetMapping
    @Operation(summary = "List testimonials")
    public List<Testimonial> list() {
        return testimonialRepository.findAll();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get testimonial by id", responses = {
            @ApiResponse(responseCode = "200", description = "Found", content = @Content(schema = @Schema(implementation = Testimonial.class))),
            @ApiResponse(responseCode = "404", description = "Not Found")
    })
    public Testimonial get(@Parameter(description = "Testimonial id") @PathVariable Integer id) {
        return testimonialRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Testimonial " + id + " not found"));
    }

    @PostMapping
    @Operation(summary = "Create a testimonial")
    public Testimonial create(@RequestBody Testimonial t) {
        return testimonialRepository.save(t);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update a testimonial")
    public Testimonial update(@Parameter(description = "Testimonial id") @PathVariable Integer id,
                              @RequestBody Testimonial t) {
        Testimonial existing = testimonialRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Testimonial " + id + " not found"));
        existing.setTitle(t.getTitle());
        existing.setCreatedAt(t.getCreatedAt());
        existing.setBody(t.getBody());
        existing.setCreatedBy(t.getCreatedBy());
        return testimonialRepository.save(existing);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a testimonial")
    public void delete(@Parameter(description = "Testimonial id") @PathVariable Integer id) {
        testimonialRepository.deleteById(id);
    }
}
