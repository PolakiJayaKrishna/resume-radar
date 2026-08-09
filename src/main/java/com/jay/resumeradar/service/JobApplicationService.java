package com.jay.resumeradar.service;

import com.jay.resumeradar.entities.ApplicationStatus;
import com.jay.resumeradar.entities.JobApplication;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface JobApplicationService {
    JobApplication createJobApplication(Long userId, String applicationName, String jobLink, ApplicationStatus status);
    List<JobApplication> getAllApplications(Long userId);
    JobApplication getById(Long id);
    JobApplication updateApplication(Long id ,String applicationName,ApplicationStatus status);
    void deleteApplication(Long id);
}
