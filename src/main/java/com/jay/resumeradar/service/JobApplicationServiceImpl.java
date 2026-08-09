package com.jay.resumeradar.service;

import com.jay.resumeradar.entities.ApplicationStatus;
import com.jay.resumeradar.entities.JobApplication;
import com.jay.resumeradar.exception.ResourceNotFoundException;
import com.jay.resumeradar.repository.JobApplicationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class JobApplicationServiceImpl implements JobApplicationService{

    private final JobApplicationRepository jobApplicationRepository;

    @Override
    public JobApplication createJobApplication(Long userId, String applicationName, String jobLink, ApplicationStatus status) {
        return jobApplicationRepository.save(JobApplication.builder()
                .userId(userId)
                .applicationName(applicationName)
                .jobLink(jobLink)
                .status(status)
                .build());
    }

    @Override
    public List<JobApplication> getAllJobApplications(Long userId) {
        return jobApplicationRepository.findByUserId(userId);
    }

    @Override
    public JobApplication getById(Long id , Long userId) {

        JobApplication jobApplication = jobApplicationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("JobApplication not found with id: " + id));

        if(!jobApplication.getUserId().equals(userId)) throw new ResourceNotFoundException("Application not found or access denied");

        return jobApplication;
    }

    @Override
    public JobApplication updateJobApplication(Long id, String applicationName, ApplicationStatus status, Long userId) {
        JobApplication existing = jobApplicationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("JobApplication not found with id: " + id));
        existing.setApplicationName(applicationName);
        existing.setStatus(status);

        if(!existing.getUserId().equals(userId)) throw new ResourceNotFoundException("Application not found or access denied");

        return jobApplicationRepository.save(existing);
    }

    @Override
    public void deleteJobApplication(Long id, Long userId) {
        JobApplication jobApplication = jobApplicationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("JobApplication not found"));

        if(!jobApplication.getUserId().equals(userId)) throw new ResourceNotFoundException("Application not found or access denied");
        
        jobApplicationRepository.delete(jobApplication);
    }
}
