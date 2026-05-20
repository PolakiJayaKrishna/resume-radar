package com.jay.resumeradar.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GeminiAnalysisDto {
    private int matchScore;
    private List<String>  missingKeywords;
    private List<String> weakAreas;
    private List<String>  recommendations;
    private String rewrittenSummary;
}
