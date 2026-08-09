package com.jay.resumeradar.controller;

import com.jay.resumeradar.dto.JobApplicationRequest;
import com.jay.resumeradar.entities.JobApplication;
import com.jay.resumeradar.entities.User;
import com.jay.resumeradar.service.JobApplicationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/applications")
@CrossOrigin("*")
@RequiredArgsConstructor
public class JobApplicationController {
    private final JobApplicationService jobApplicationService;

    @PostMapping()
    public ResponseEntity<JobApplication> createJobApplication(@Valid @RequestBody JobApplicationRequest request){
        User currentUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        JobApplication result = jobApplicationService.createJobApplication(currentUser.getId(), request.getApplicationName(), request.getJobLink(), request.getStatus());
        return ResponseEntity.status(HttpStatus.CREATED).body(result);
    }
}
