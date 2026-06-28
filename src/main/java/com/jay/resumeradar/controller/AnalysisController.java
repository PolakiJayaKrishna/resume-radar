package com.jay.resumeradar.controller;

import com.jay.resumeradar.entities.AnalysisResult;
import com.jay.resumeradar.service.AnalysisService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

// Security Note: "*" is for demo/portfolio purposes. In production, restrict to exact frontend domain.
@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/analysis")
@RequiredArgsConstructor
@Tag(name = "Analysis Controller", description = "Endpoints for AI Resume Analysis")
@SecurityRequirement(name = "bearerAuth")
public class AnalysisController {
    private final AnalysisService analysisService;

    @Operation(summary = "Submit a resume for AI analysis", description = "Starts an asynchronous background process and returns an ID for polling.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "202", description = "Analysis accepted and processing started in background"),
            @ApiResponse(responseCode = "400", description = "Invalid input or missing file"),
            @ApiResponse(responseCode = "401", description = "Unauthorized (Missing or invalid JWT)")
    })
    @PostMapping("/score")
    public ResponseEntity<Long> analysisResume(@RequestParam Long resumeId, @RequestParam String jobDescription){
        Long id = analysisService.analyzeResume(resumeId,jobDescription);
        return ResponseEntity.accepted().body(id); //202 Immediately
    }

    @Operation(summary = "Poll analysis status", description = "Checks the database for the analysis status. Returns PENDING or the completed JSON analysis.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved status (PENDING or COMPLETED)"),
            @ApiResponse(responseCode = "404", description = "Analysis ID not found"),
            @ApiResponse(responseCode = "401", description = "Unauthorized (Missing or invalid JWT)")
    })
    @GetMapping("/{id}")
    public ResponseEntity<AnalysisResult> findStatusById(@PathVariable Long id){
        return ResponseEntity.ok(analysisService.getResult(id));
    }

    @Operation(summary = "Get analysis history for a resume")
    @GetMapping("/history/{resumeId}")
    public ResponseEntity<List<AnalysisResult>> getHistory(@PathVariable Long resumeId) {
        return ResponseEntity.ok(analysisService.getHistoryByResumeId(resumeId));
    }

}
