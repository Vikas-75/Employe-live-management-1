package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.service.LeaveService;
import com.example.demo.models.Employee;
import com.example.demo.models.LeaveRequest;
import com.example.demo.repository.EmployeeRepository;

@RestController
@CrossOrigin(origins = "https://auth-frontend4.vercel.app/")
@RequestMapping("/employee/leave")
public class EmployeeLeaveController {

    @Autowired
    private LeaveService leaveService;

    @Autowired
    private EmployeeRepository employeeRepo;

    @PostMapping("/apply/{empId}")
    public LeaveRequest applyLeave(
            @PathVariable Long empId,
            @RequestBody LeaveRequest leave) {

        Employee emp = employeeRepo.findById(empId)
                .orElseThrow(() -> new RuntimeException("Employee not found"));

        leave.setEmployee(emp);
        return leaveService.applyLeave(leave);
    }

    @GetMapping("/my/{empId}")
    public List<LeaveRequest> myLeaves(@PathVariable Long empId) {
        return leaveService.getEmployeeLeaves(empId);
    }
}
