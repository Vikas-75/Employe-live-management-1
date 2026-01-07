package com.example.demo.controller;

import com.example.demo.models.Attendance;
import com.example.demo.dto.AttendanceRequest;
import com.example.demo.service.AttendanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
    @CrossOrigin(origins = "https://auth-frontend4.vercel.app")
@RequestMapping("/")
public class AttendanceController {

    @Autowired
    private AttendanceService attendanceService;

    // ================= ADMIN =================

    @PostMapping("/admin/attendance/mark")
    public Attendance markByAdmin(@RequestBody AttendanceRequest req) {
        return attendanceService.markAttendance(
                req.employeeId,
                LocalDate.parse(req.date),
                req.present,
                req.remarks
        );
    }

    @GetMapping("/admin/attendance/date/{date}")
    public List<Attendance> getAttendanceByDate(@PathVariable String date) {
        return attendanceService.getAttendanceByDate(LocalDate.parse(date));
    }

    // ================= EMPLOYEE =================

    // Mark attendance (QR / Self)
    @PostMapping("/employee/attendance/mark")
    public Attendance markByEmployee(@RequestParam Long employeeId) {
        return attendanceService.markAttendance(
                employeeId,
                LocalDate.now(),
                true,
                "Self / QR Mark"
        );
    }

    // View all attendance (latest first)
    @GetMapping("/employee/attendance/{employeeId}")
    public List<Attendance> getMyAttendance(@PathVariable Long employeeId) {
        return attendanceService.getEmployeeAttendance(employeeId);
    }

    // View attendance for a specific day
    @GetMapping("/employee/attendance/{employeeId}/day/{date}")
    public Attendance getAttendanceByDay(
            @PathVariable Long employeeId,
            @PathVariable String date) {
        return attendanceService.getEmployeeAttendanceByDate(employeeId, LocalDate.parse(date));
    }

    // View attendance for a specific month
    @GetMapping("/employee/attendance/{employeeId}/month/{year}/{month}")
    public List<Attendance> getAttendanceByMonth(
            @PathVariable Long employeeId,
            @PathVariable int year,
            @PathVariable int month) {
        return attendanceService.getEmployeeAttendanceByMonth(employeeId, year, month);
    }
}
