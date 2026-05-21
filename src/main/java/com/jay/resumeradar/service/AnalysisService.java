package com.jay.resumeradar.service;

import com.jay.resumeradar.entities.AnalysisResult;

public interface AnalysisService {

    Long analyzeResume(Long resumeId, String jobDescription);

    AnalysisResult getResult(Long id);
}
