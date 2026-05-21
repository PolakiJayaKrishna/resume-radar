package com.jay.resumeradar.controller;

import com.jay.resumeradar.service.AnalysisService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/analysis")
@RequiredArgsConstructor
public class AnalysisController {
    private final AnalysisService analysisService;

    @PostMapping("/score")
    public ResponseEntity<Void> AnalysisResume(@RequestParam Long resumeId, @RequestParam String jobDescription){
        analysisService.analyzeResume(resumeId,jobDescription);

        return ResponseEntity.accepted().build(); //202 Immediately
    }
}
