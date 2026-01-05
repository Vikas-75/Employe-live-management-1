package com.example.demo.controller;



import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.example.demo.models.LeaveRequest;
import com.example.demo.models.LeaveStatus;
import com.example.demo.service.LeaveService;



@RestController
@RequestMapping("/admin/leave")
@CrossOrigin(origins = "hhttps://auth-frontend4.vercel.app/")
public class AdminLeaveController {

    @Autowired
    private LeaveService leaveService;

  
    @GetMapping("/pending")
    public List<LeaveRequest> getPendingLeaves() {
        return leaveService.getAllLeaves()
                .stream()
                .filter(l -> l.getStatus() == LeaveStatus.PENDING)
                .toList();
    }

    
    @PutMapping("/{id}/approve")
    public LeaveRequest approveLeave(@PathVariable Long id) {
        return leaveService.updateStatus(id, LeaveStatus.APPROVED);
    }

  
    @PutMapping("/{id}/reject")
    public LeaveRequest rejectLeave(@PathVariable Long id) {
        return leaveService.updateStatus(id, LeaveStatus.REJECTED);
    }

   
    @GetMapping("/history")
    public List<LeaveRequest> getAllLeaveHistory() {
        return leaveService.getAllLeaves();
    }
}
