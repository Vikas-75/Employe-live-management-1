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
    public void deleteEmployee(@PathVariable Long id) {
        service.deleteEmployee(id);
    }
}
