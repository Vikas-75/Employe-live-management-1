package com.example.demo.service;

import com.example.demo.models.Attendance;
import com.example.demo.models.Employee;
import com.example.demo.repository.AttendanceRepository;
import com.example.demo.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;

@Service
public class AttendanceService {

    @Autowired
    private AttendanceRepository attendanceRepo;

    @Autowired
    private EmployeeRepository employeeRepo;

    // ================= MARK ATTENDANCE =================
    public Attendance markAttendance(
            Long employeeId,
            LocalDate date,
            boolean present,
            String remarks
    ) {
        // ✅ Validate employee exists
        Employee employee = employeeRepo.findById(employeeId)
                .orElseThrow(() -> new RuntimeException("Employee not found"));

        // ✅ Check if attendance already exists for that date
        Attendance existing = attendanceRepo.findByEmployeeAndDate(employee, date);

        if (existing != null) {
            // Update existing
            existing.setPresent(present);
            existing.setRemarks(remarks);
            return attendanceRepo.save(existing);
        }

        // ✅ Create new attendance
        Attendance attendance = new Attendance();
        attendance.setEmployee(employee);
        attendance.setDate(date);
        attendance.setPresent(present);
        attendance.setRemarks(remarks);

        return attendanceRepo.save(attendance);
    }

    // ================= EMPLOYEE HISTORY =================
    // Get all attendance of employee
    public List<Attendance> getEmployeeAttendance(Long employeeId) {
        // Validate employee exists
        employeeRepo.findById(employeeId)
                .orElseThrow(() -> new RuntimeException("Employee not found"));

        return attendanceRepo.findByEmployeeIdOrderByDateDesc(employeeId);
    }

    // Get attendance for a specific day
    public Attendance getEmployeeAttendanceByDate(Long employeeId, LocalDate date) {
        Employee employee = employeeRepo.findById(employeeId)
                .orElseThrow(() -> new RuntimeException("Employee not found"));

        return attendanceRepo.findByEmployeeAndDate(employee, date);
    }

    // Get attendance for a specific month
    public List<Attendance> getEmployeeAttendanceByMonth(Long employeeId, int year, int month) {
        Employee employee = employeeRepo.findById(employeeId)
                .orElseThrow(() -> new RuntimeException("Employee not found"));

        YearMonth ym = YearMonth.of(year, month);
        LocalDate start = ym.atDay(1);
        LocalDate end = ym.atEndOfMonth();

        return attendanceRepo.findByEmployeeAndDateBetweenOrderByDateAsc(employee, start, end);
    }

    // ================= ADMIN DATE VIEW =================
    public List<Attendance> getAttendanceByDate(LocalDate date) {
        return attendanceRepo.findByDate(date);
    }
}
