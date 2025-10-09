package com.example.lugnutsautomotive.domain;

import jakarta.persistence.*;

@Entity
public class Employee {
    @Id
    private Integer employeeNumber;

    @Column(length = 50, nullable = false)
    private String lastName;

    @Column(length = 50, nullable = false)
    private String firstName;

    @Column(length = 10, nullable = false)
    private String extension;

    @Column(length = 100, nullable = false)
    private String email;

    @ManyToOne(optional = false)
    @JoinColumn(name = "officeCode", nullable = false)
    private Office office;

    @ManyToOne
    @JoinColumn(name = "reportsTo")
    private Employee reportsTo;

    @Column(length = 50, nullable = false)
    private String jobTitle;

    public Integer getEmployeeNumber() { return employeeNumber; }
    public void setEmployeeNumber(Integer employeeNumber) { this.employeeNumber = employeeNumber; }
    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }
    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }
    public String getExtension() { return extension; }
    public void setExtension(String extension) { this.extension = extension; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public Office getOffice() { return office; }
    public void setOffice(Office office) { this.office = office; }
    public Employee getReportsTo() { return reportsTo; }
    public void setReportsTo(Employee reportsTo) { this.reportsTo = reportsTo; }
    public String getJobTitle() { return jobTitle; }
    public void setJobTitle(String jobTitle) { this.jobTitle = jobTitle; }
}