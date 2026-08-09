package com.jay.resumeradar.service;

import com.jay.resumeradar.entities.ApplicationStatus;
import com.jay.resumeradar.entities.JobApplication;
import java.util.List;

public interface JobApplicationService {
    JobApplication createJobApplication(Long userId, String applicationName, String jobLink, ApplicationStatus status);
    List<JobApplication> getAllJobApplications(Long userId);
    JobApplication getById(Long id, Long userId);
    JobApplication updateJobApplication(Long id ,String applicationName,ApplicationStatus status, Long userId);
    void deleteJobApplication(Long id, Long userId);
}
