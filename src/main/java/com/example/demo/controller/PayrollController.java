package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.models.Employee;
import com.example.demo.models.Payroll;
import com.example.demo.service.EmployeeService;
import com.example.demo.service.PayrollService;

@RestController
    @CrossOrigin(origins = "https://auth-frontend4.vercel.app")
@RequestMapping("/")
public class PayrollController {

    @Autowired
    private PayrollService payrollService;
    
    @PostMapping("/admin/payroll/generate")
    public ResponseEntity<?> generatePayroll(
            @RequestParam Long employeeId,
            @RequestParam int month,
            @RequestParam int year,
            @RequestParam(defaultValue = "0") double bonus
    ) {
        Payroll payroll = payrollService.generatePayroll(employeeId, month, year, bonus);
        return ResponseEntity.ok(payroll);
    }

    // 🔹 Get salary history of an employee
    @GetMapping("/admin/payroll/history/{employeeId}")
    public List<Payroll> salaryHistory(@PathVariable Long employeeId) {
        return payrollService.getSalaryHistory(employeeId);
    }
    
    
    @GetMapping("/employee/payroll/my/{employeeId}")
    public List<Payroll> getMyPayrolls(@PathVariable Long employeeId) {
        // Optional: log for debugging
        System.out.println("Fetching payrolls for employee ID: " + employeeId);
        
        List<Payroll> payrolls = payrollService.getSalaryHistory(employeeId);
        
        if (payrolls.isEmpty()) {
            System.out.println("No payrolls found!");
        }
        
        return payrolls;
    }

}
