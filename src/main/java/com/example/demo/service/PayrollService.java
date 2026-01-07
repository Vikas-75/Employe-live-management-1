package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.models.Employee;
import com.example.demo.models.Payroll;
import com.example.demo.repository.AttendanceRepository;
import com.example.demo.repository.EmployeeRepository;
import com.example.demo.repository.PayrollRepository;

@Service
public class PayrollService {

    @Autowired
    private AttendanceRepository attendanceRepo;

    @Autowired
    private EmployeeRepository employeeRepo;

    @Autowired
    private PayrollRepository payrollRepo;

    /**
     * Generate payroll for an employee for a specific month/year with bonus.
     */
    public Payroll generatePayroll(Long employeeId, int month, int year, double bonus) {

        Employee employee = employeeRepo.findById(employeeId)
                .orElseThrow(() -> new RuntimeException("Employee not found"));

        // ✅ Use the correct repository method
        long presentDays = attendanceRepo.countAttendanceByMonthAndYear(employeeId, month, year);

        double baseSalary = employee.getSalary();
        double perDaySalary = baseSalary / 30; // Assuming 30 days/month
        double earnedSalary = perDaySalary * presentDays;

        double grossSalary = earnedSalary + bonus;
        double deductions = baseSalary - earnedSalary;
        double netSalary = grossSalary - deductions;

        Payroll payroll = new Payroll();
        payroll.setEmployee(employee);
        payroll.setMonth(month);
        payroll.setYear(year);
        payroll.setBaseSalary(baseSalary);
        payroll.setBonus(bonus);
        payroll.setTotalDeductions(deductions);
        payroll.setGrossSalary(grossSalary);
        payroll.setNetSalary(netSalary);

        return payrollRepo.save(payroll);
    }

    /**
     * Fetch salary history for an employee.
     */
    public List<Payroll> getSalaryHistory(Long employeeId) {
        employeeRepo.findById(employeeId)
                .orElseThrow(() -> new RuntimeException("Employee not found"));

        return payrollRepo.findByEmployeeIdOrderByYearDescMonthDesc(employeeId);
    }
}
