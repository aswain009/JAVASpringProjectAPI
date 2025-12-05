package com.example.lugnutsautomotive.web;

import com.example.lugnutsautomotive.common.NotFoundException;
import com.example.lugnutsautomotive.domain.Employee;
import com.example.lugnutsautomotive.domain.Office;
import com.example.lugnutsautomotive.repository.EmployeeRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/lugnuts/employees")
@Tag(name = "Employees", description = "Operations on employees")
public class EmployeeController {

    private final EmployeeRepository employeeRepository;

    public EmployeeController(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    @GetMapping
    @Operation(summary = "List employees", description = "Returns all employees in the salesRep structure")
    public List<Map<String, Object>> list() {
        List<Employee> employees = employeeRepository.findAll();
        List<Map<String, Object>> result = new ArrayList<>();

        for (Employee selectedRep : employees) {
            Office repOffice = selectedRep.getOffice();
            Map<String, Object> officeMap = new LinkedHashMap<>();
            officeMap.put("officeCode", orElse(repOffice != null ? repOffice.getOfficeCode() : null, ""));
            officeMap.put("city", orElse(repOffice != null ? repOffice.getCity() : null, ""));
            officeMap.put("phone", orElse(repOffice != null ? repOffice.getPhone() : null, ""));
            officeMap.put("addressLine1", orElse(repOffice != null ? repOffice.getAddressLine1() : null, ""));
            officeMap.put("addressLine2", orElse(repOffice != null ? repOffice.getAddressLine2() : null, ""));
            officeMap.put("state", orElse(repOffice != null ? repOffice.getState() : null, ""));
            officeMap.put("country", orElse(repOffice != null ? repOffice.getCountry() : null, ""));
            officeMap.put("postalCode", orElse(repOffice != null ? repOffice.getPostalCode() : null, ""));
            officeMap.put("territory", orElse(repOffice != null ? repOffice.getTerritory() : null, ""));

            Map<String, Object> salesRepMap = new LinkedHashMap<>();
            salesRepMap.put("employeeNumber", selectedRep.getEmployeeNumber() != null ? selectedRep.getEmployeeNumber() : 0);
            salesRepMap.put("lastName", orElse(selectedRep.getLastName(), ""));
            salesRepMap.put("firstName", orElse(selectedRep.getFirstName(), ""));
            salesRepMap.put("extension", orElse(selectedRep.getExtension(), ""));
            salesRepMap.put("email", orElse(selectedRep.getEmail(), ""));
            salesRepMap.put("office", officeMap);
            salesRepMap.put("reportsTo", selectedRep.getReportsTo() != null ? selectedRep.getReportsTo().getEmployeeNumber() : "");
            salesRepMap.put("jobTitle", orElse(selectedRep.getJobTitle(), ""));

            Map<String, Object> response = new LinkedHashMap<>();
            response.put("salesRep", salesRepMap);
            result.add(response);
        }

        return result;
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get employee by id", responses = {
            @ApiResponse(responseCode = "200", description = "Found", content = @Content(schema = @Schema(implementation = Map.class))),
            @ApiResponse(responseCode = "404", description = "Not Found")
    })
    public Map<String, Object> get(@Parameter(description = "Employee number") @PathVariable Integer id) {
        Employee selectedRep = employeeRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Employee " + id + " not found"));

        Office repOffice = selectedRep.getOffice();
        Map<String, Object> officeMap = new LinkedHashMap<>();
        officeMap.put("officeCode", orElse(repOffice != null ? repOffice.getOfficeCode() : null, ""));
        officeMap.put("city", orElse(repOffice != null ? repOffice.getCity() : null, ""));
        officeMap.put("phone", orElse(repOffice != null ? repOffice.getPhone() : null, ""));
        officeMap.put("addressLine1", orElse(repOffice != null ? repOffice.getAddressLine1() : null, ""));
        officeMap.put("addressLine2", orElse(repOffice != null ? repOffice.getAddressLine2() : null, ""));
        officeMap.put("state", orElse(repOffice != null ? repOffice.getState() : null, ""));
        officeMap.put("country", orElse(repOffice != null ? repOffice.getCountry() : null, ""));
        officeMap.put("postalCode", orElse(repOffice != null ? repOffice.getPostalCode() : null, ""));
        officeMap.put("territory", orElse(repOffice != null ? repOffice.getTerritory() : null, ""));

        Map<String, Object> salesRepMap = new LinkedHashMap<>();
        salesRepMap.put("employeeNumber", selectedRep.getEmployeeNumber() != null ? selectedRep.getEmployeeNumber() : 0);
        salesRepMap.put("lastName", orElse(selectedRep.getLastName(), ""));
        salesRepMap.put("firstName", orElse(selectedRep.getFirstName(), ""));
        salesRepMap.put("extension", orElse(selectedRep.getExtension(), ""));
        salesRepMap.put("email", orElse(selectedRep.getEmail(), ""));
        salesRepMap.put("office", officeMap);
        salesRepMap.put("reportsTo", selectedRep.getReportsTo() != null ? selectedRep.getReportsTo().getEmployeeNumber() : "");
        salesRepMap.put("jobTitle", orElse(selectedRep.getJobTitle(), ""));

        Map<String, Object> response = new LinkedHashMap<>();
        response.put("salesRep", salesRepMap);
        return response;
    }

    private static String orElse(String value, String defaultValue) {
        return value != null ? value : defaultValue;
    }
}
