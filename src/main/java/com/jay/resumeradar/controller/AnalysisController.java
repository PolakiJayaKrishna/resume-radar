package com.jay.resumeradar.controller;

import com.jay.resumeradar.entities.AnalysisResult;
import com.jay.resumeradar.repository.AnalysisResultRepository;
import com.jay.resumeradar.service.AnalysisService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/analysis")
@RequiredArgsConstructor
public class AnalysisController {
    private final AnalysisService analysisService;

    @PostMapping("/score")
    public ResponseEntity<Long> analysisResume(@RequestParam Long resumeId, @RequestParam String jobDescription){
        Long id = analysisService.analyzeResume(resumeId,jobDescription);
        return ResponseEntity.accepted().body(id); //202 Immediately
    }

    @GetMapping("/{id}")
    public ResponseEntity<AnalysisResult> findStatusById(@PathVariable Long id){
        return ResponseEntity.ok(analysisService.getResult(id));
    }
}
