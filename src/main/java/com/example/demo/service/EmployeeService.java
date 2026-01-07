package com.example.demo.service;

import com.example.demo.models.Employee;
import com.example.demo.models.EmployeeStatus;
import com.example.demo.models.RoleType;
import com.example.demo.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeService {

    @Autowired
    private EmployeeRepository repository;

    // ================== REGISTRATION ==================
    public Employee register(Employee emp) {

        if (repository.findByEmail(emp.getEmail()).isPresent()) {
            throw new RuntimeException("Email already registered");
        }

        emp.setStatus(EmployeeStatus.LIVE); // waiting for admin approval
        emp.setApproved(false);

        if (emp.getRoleType() == null) {
            emp.setRoleType(RoleType.EMPLOYEE);
        }

        return repository.save(emp);
    }

    // ================== LOGIN ==================
    public Employee login(String email, String password, RoleType roleType) {

        Employee emp = repository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not registered"));

        if (!emp.getPassword().equals(password)) {
            throw new RuntimeException("Invalid password");
        }

        if (!emp.getRoleType().equals(roleType)) {
            throw new RuntimeException("Role mismatch");
        }

        // 🔐 ADMIN APPROVAL CHECK
        if (!emp.isApproved() || emp.getStatus() != EmployeeStatus.LIVE) {
            throw new RuntimeException("User not approved yet");
        }

        return emp;
    }

    // ================== ADMIN ADD EMPLOYEE ==================
    public Employee addEmployeeByAdmin(Employee emp) {

        if (repository.findByEmail(emp.getEmail()).isPresent()) {
            throw new RuntimeException("Email already registered");
        }

        emp.setApproved(true);
        emp.setStatus(EmployeeStatus.LIVE);

        if (emp.getRoleType() == null) {
            emp.setRoleType(RoleType.EMPLOYEE);
        }

        return repository.save(emp);
    }

    // ================== GET ALL EMPLOYEES ==================
    public List<Employee> getAllEmployees() {
        return repository.findAll();
    }

    // ================== GET EMPLOYEE BY ID ==================
    public Employee getEmployeeById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Employee not found"));
    }

    // ================== UPDATE EMPLOYEE ==================
    public Employee updateEmployee(Long id, Employee updated) {

        Employee emp = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Employee not found"));

        emp.setName(updated.getName());
        emp.setEmail(updated.getEmail());
        emp.setRole(updated.getRole());
        emp.setDepartment(updated.getDepartment());
        emp.setSalary(updated.getSalary());
        emp.setRoleType(updated.getRoleType());

        return repository.save(emp);
    }

    // ================== DELETE EMPLOYEE ==================
    public void deleteEmployee(Long id) {
        if (!repository.existsById(id)) {
            throw new RuntimeException("Employee not found");
        }
        repository.deleteById(id);
    }

    // ================== REQUEST LIVE (OPTIONAL) ==================
    public Employee requestLive(Long id) {
        Employee emp = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Employee not found"));

        emp.setStatus(EmployeeStatus.PENDING);
        emp.setApproved(false);

        return repository.save(emp);
    }

    // ================== ADMIN APPROVE EMPLOYEE ==================
    public Employee approveEmployee(Long id) {
        Employee emp = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Employee not found"));

        emp.setStatus(EmployeeStatus.LIVE);
        emp.setApproved(true);

        return repository.save(emp);
    }

    // ================== ADMIN REJECT EMPLOYEE ==================
    public Employee rejectEmployee(Long id) {
        Employee emp = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Employee not found"));

        emp.setStatus(EmployeeStatus.REJECTED);
        emp.setApproved(false);

        return repository.save(emp);
    }

    // ================== GET PENDING EMPLOYEES ==================
    public List<Employee> getPendingEmployees() {
        return repository.findByStatus(EmployeeStatus.PENDING);
    }
    
    public List<Employee> getPendingUsers() {
        return repository.findByApprovedFalse();
    }

    public Employee approveUser(Long id) {
        Employee emp = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
        emp.setApproved(true);
        return repository.save(emp);
    }

    public void rejectUser(Long id) {
    	repository.deleteById(id); // OR mark rejected
    }
}
