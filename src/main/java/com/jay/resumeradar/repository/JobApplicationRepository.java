package com.jay.resumeradar.repository;

import com.jay.resumeradar.entities.ApplicationStatus;
import com.jay.resumeradar.entities.JobApplication;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface JobApplicationRepository extends JpaRepository<JobApplication, Long> {
    List<JobApplication> findByUserId(Long userId);
    List<JobApplication> findByUserIdAndStatus(Long userId, ApplicationStatus applicationStatus);
    List<JobApplication> findByUserIdAndApplicationNameContainingIgnoreCase(Long userId, String applicationName);
}
