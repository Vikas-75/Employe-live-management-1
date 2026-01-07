package com.example.demo.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.models.Employee;
import com.example.demo.models.Payroll;

public interface PayrollRepository extends JpaRepository<Payroll, Long> {
    List<Payroll> findByEmployeeId(Long employeeId);
    List<Payroll> findByEmployeeIdOrderByYearDescMonthDesc(Long employeeId);
    Payroll findByEmployee(Employee employee);
    Optional<Payroll> findByEmployeeIdAndMonthAndYear(Long employeeId, int month, int year);
    long countByEmployeeIdAndMonthAndYear(Long employeeId, int month, int year);


    Payroll findByEmployeeAndMonthAndYear(Employee employee, int month, int year);
}

