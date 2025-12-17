package com.example.lugnutsautomotive.domain;

import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDateTime;

@Entity
@Table(name = "testimonial")
public class Testimonial {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @Schema(accessMode = Schema.AccessMode.READ_ONLY, description = "Database-generated primary key")
    private Integer id;

    @Column(length = 800)
    private String title;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(length = 5000)
    private String body;

    @Column(name = "created_by", length = 500)
    private String createdBy;

    public Integer getId() { return id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public String getBody() { return body; }
    public void setBody(String body) { this.body = body; }

    public String getCreatedBy() { return createdBy; }
    public void setCreatedBy(String createdBy) { this.createdBy = createdBy; }
}
