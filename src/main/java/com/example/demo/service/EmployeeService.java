package com.example.demo.service;

import com.example.demo.models.Employee;
import com.example.demo.models.RoleType;
import com.example.demo.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
public class EmployeeService {

    @Autowired
    private EmployeeRepository repository;

    // ================== REGISTRATION ==================
    public Employee register(Employee emp) {

        if (repository.findByEmail(emp.getEmail()).isPresent()) {
            throw new RuntimeException("Email already registered");
        }

        emp.setApproved(false);       
        emp.setStatus("OFFLINE");

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

        if (!emp.isApproved()) {
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
        emp.setStatus("OFFLINE");      

        if (emp.getRoleType() == null) {
            emp.setRoleType(RoleType.EMPLOYEE);
        }
        return repository.save(emp);
    }

//--------------------------------------------------------------------
    public List<Employee> getAllEmployees() {
        return repository.findAll();
    }
//---------------------------------------------------------------------
    public Employee getEmployeeById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Employee not found"));
    }
    //------------------------------------------------------------------
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

//----------------------------------------------------------------------------
    public void deleteEmployee(Long id) {
        if (!repository.existsById(id)) {
            throw new RuntimeException("Employee not found");
        }
        repository.deleteById(id);
    }

//------------------------------------------------------------------------------------
    public Employee requestLive(Long id) {
        Employee emp = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Employee not found"));
        emp.setStatus("PENDING");
        emp.setApproved(false);
        return repository.save(emp);
    }
//--------------------------------------------------------------------------------
    public Employee approveEmployee(Long id) {
        Employee emp = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Employee not found"));
        emp.setStatus("LIVE");
        emp.setApproved(true);
        return repository.save(emp);
    }
//----------------------------------------------------------------------------------
    public Employee rejectEmployee(Long id) {
        Employee emp = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Employee not found"));
        emp.setStatus("REJECTED");
        emp.setApproved(false);
        return repository.save(emp);
    }
//-------------------------------------------------------------------------------
    public List<Employee> getPendingEmployees() {
        return repository.findByStatus("PENDING");
    } 
}
