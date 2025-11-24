package com.example.lugnutsautomotive.domain;

import jakarta.persistence.*;

@Entity
@Table(name = "admin")
public class Admin {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="user_name",length = 50, nullable = false, unique = true)
    private String userName;

    @Column(length = 100, nullable = false)
    private String password;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getUsername() { return userName; }
    public void setUsername(String username) { this.userName = username; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
}