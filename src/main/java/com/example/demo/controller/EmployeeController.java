package com.example.demo.controller;

import com.example.demo.service.EmployeeService;
import com.example.demo.dto.LoginRequest;
import com.example.demo.models.Employee;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.lang.String;
import java.util.List;
import java.util.Map;
@RestController
    @CrossOrigin(origins = "https://auth-frontend4.vercel.app/")
@RequestMapping("/employee")

public class EmployeeController {

    private final EmployeeService service;

    public EmployeeController(EmployeeService service) {
        this.service = service;
    }

    /* ================= REGISTER ================= */
    @PostMapping("/register")
    public ResponseEntity<Employee> register(@RequestBody Employee emp) {
        return ResponseEntity.ok(service.register(emp));
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest req) {
        try {
            Employee emp = service.login(req.getEmail(), req.getPassword(), req.getRoleType());
            return ResponseEntity.ok(emp);
        } catch (RuntimeException ex) {
            // send error message to frontend
            return ResponseEntity.status(401).body(Map.of("error", ex.getMessage()));
        }
    }
    
    
    @GetMapping("/all")
    public ResponseEntity<List<Employee>> getAllEmployees() {
        return ResponseEntity.ok(service.getAllEmployees());
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<?> getEmployeeById(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(service.getEmployeeById(id));
        } catch (RuntimeException ex) {
            return ResponseEntity.status(404).body(Map.of("error", ex.getMessage()));
        }
    }


    /* ================= OTHER APIs ================= */
    @PutMapping("/{id}/request-live")
    public ResponseEntity<Employee> requestLive(@PathVariable Long id) {
        return ResponseEntity.ok(service.requestLive(id));
    }

    @PutMapping("/{id}/approve")
    public ResponseEntity<Employee> approveEmployee(@PathVariable Long id) {
        return ResponseEntity.ok(service.approveEmployee(id));
    }

    @PutMapping("/{id}/reject")
    public ResponseEntity<Employee> rejectEmployee(@PathVariable Long id) {
        return ResponseEntity.ok(service.rejectEmployee(id));
    }

    @GetMapping("/pending")
    public ResponseEntity<List<Employee>> getPendingEmployees() {
        return ResponseEntity.ok(service.getPendingEmployees());
    }
}
