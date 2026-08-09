package com.jay.resumeradar.dto;

import com.jay.resumeradar.entities.ApplicationStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class JobApplicationRequest {
    private String applicationName;
    private String jobLink;
    private ApplicationStatus status;
}
