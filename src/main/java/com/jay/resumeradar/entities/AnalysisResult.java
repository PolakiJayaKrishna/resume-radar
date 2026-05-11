package com.jay.resumeradar.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
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

    @Column(name = "analyzed_at", nullable = false, updatable = false)
    private LocalDateTime analyzedAt;
}
