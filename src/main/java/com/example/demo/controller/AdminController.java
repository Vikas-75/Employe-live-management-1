package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.example.demo.service.EmployeeService;
import com.example.demo.models.Employee;

@RestController
@RequestMapping("/admin/employees")
@CrossOrigin(origins = "https://auth-frontend4.vercel.app")
public class AdminController {

@Autowired
    private EmployeeService service;

  
    @PostMapping("/add")
    public Employee addEmployee(@RequestBody Employee employee) {
        return service.addEmployeeByAdmin(employee);
    }

   
    @GetMapping
    public List<Employee> getAllEmployees() {
        return service.getAllEmployees();
    }

  
    @GetMapping("/{id}")
    public Employee getEmployee(@PathVariable Long id) {
        return service.getEmployeeById(id);
    }

    
    @PutMapping("/{id}")
    public Employee updateEmployee(
            @PathVariable Long id,
            @RequestBody Employee employee) {
        return service.updateEmployee(id, employee);
    }
 @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteEmployee(@PathVariable Long id) {
        try {
            service.deleteEmployee(id);
            return ResponseEntity.ok("Employee deleted successfully along with all related data.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to delete employee: " + e.getMessage());
        }
    }
    @GetMapping("/pending")
    public List<Employee> getPendingUsers() {
        return service.getPendingUsers();
    }

    // 2️⃣ Approve user
    @PutMapping("/approve/{id}")
    public Employee approveUser(@PathVariable Long id) {
        return service.approveUser(id);
    }

    // 3️⃣ Reject user (optional – delete or mark rejected)
    @PutMapping("/reject/{id}")
    public void rejectUser(@PathVariable Long id) {
        service.rejectUser(id);
    }
    
}
