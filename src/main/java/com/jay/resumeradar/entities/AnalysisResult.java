package com.jay.resumeradar.entities;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Builder
@Data
@Table(name = "analysis_results")
@NoArgsConstructor
@AllArgsConstructor
public class AnalysisResult{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long resumeId;

    private String jobDescription;
    private int matchScore;
    @Lob
    private String missingKeywords;

    @Lob
    private String suggestions;

    @CreationTimestamp
    @Column(name = "analyzed_at", nullable = false, updatable = false)
    @JsonFormat(pattern = "dd-MM-yyyy HH:mm:ss")
    private LocalDateTime analyzedAt;
}
