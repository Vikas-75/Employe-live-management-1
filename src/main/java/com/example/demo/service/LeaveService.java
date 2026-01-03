package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.models.LeaveRequest;
import com.example.demo.models.LeaveStatus;
import com.example.demo.repository.LeaveRepository;

@Service
public class LeaveService {

    @Autowired
    private LeaveRepository leaveRepository;

    public LeaveRequest applyLeave(LeaveRequest leave) {
        leave.setStatus(LeaveStatus.PENDING);
        return leaveRepository.save(leave);
    }
//------------------------------------------------------------------------------------
    public List<LeaveRequest> getEmployeeLeaves(Long empId) {
        return leaveRepository.findByEmployeeId(empId);
    }

//--------------------------------------------------------------------
    public List<LeaveRequest> getAllLeaves() {
        return leaveRepository.findAll();
    }
//------------------------------------------------------------------------------
    public LeaveRequest updateStatus(Long id, LeaveStatus status) {
        LeaveRequest leave = leaveRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Leave not found"));
        leave.setStatus(status);
        return leaveRepository.save(leave);
    }
}