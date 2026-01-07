package com.example.demo.repository;

import com.example.demo.models.Attendance;
import com.example.demo.models.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
@Repository
public interface AttendanceRepository extends JpaRepository<Attendance, Long> {

    // Employee attendance history
    List<Attendance> findByEmployeeIdOrderByDateDesc(Long employeeId);

    // Attendance for a specific day
    Attendance findByEmployeeAndDate(Employee employee, LocalDate date);

    // Attendance between dates (month view)
    List<Attendance> findByEmployeeAndDateBetweenOrderByDateAsc(
            Employee employee,
            LocalDate start,
            LocalDate end
    );

    // Admin: attendance by date
    List<Attendance> findByDate(LocalDate date);

    // ✅ Month/Year attendance count (SAFE)
    @Query("""
        SELECT COUNT(a)
        FROM Attendance a
        WHERE a.employee.id = :employeeId
        AND MONTH(a.date) = :month
        AND YEAR(a.date) = :year
    """)
    long countAttendanceByMonthAndYear(
            @Param("employeeId") Long employeeId,
            @Param("month") int month,
            @Param("year") int year
    );
}
