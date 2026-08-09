package com.jay.resumeradar.service;

import com.jay.resumeradar.entities.ApplicationStatus;
import com.jay.resumeradar.entities.JobApplication;
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
    public List<JobApplication> getAllApplications(Long userId) {
        return List.of();
    }

    @Override
    public JobApplication getById(Long id) {
        return null;
    }

    @Override
    public JobApplication updateApplication(Long id, String applicationName, ApplicationStatus status) {
        return null;
    }

    @Override
    public void deleteApplication(Long id) {

    }
}
