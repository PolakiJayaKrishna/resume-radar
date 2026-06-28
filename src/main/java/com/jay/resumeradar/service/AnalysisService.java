package com.jay.resumeradar.service;

import com.jay.resumeradar.entities.AnalysisResult;
import java.util.List;

public interface AnalysisService {

    Long analyzeResume(Long resumeId, String jobDescription);
    AnalysisResult getResult(Long id);
    List<AnalysisResult> getHistoryByResumeId(Long resumeId);
}
