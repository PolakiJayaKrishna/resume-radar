package com.jay.resumeradar.service;

import com.jay.resumeradar.dto.GeminiRequest;
import com.jay.resumeradar.dto.GeminiResponse;
import com.jay.resumeradar.entities.AnalysisResult;
import com.jay.resumeradar.repository.AnalysisResultRepository;
import com.jay.resumeradar.repository.ResumeRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;

@Service
public class AnalysisServiceImpl implements AnalysisService {

    private final WebClient webClient;
    private final ResumeRepository resumeRepository;
    private final AnalysisResultRepository analysisResultRepository;

    // Manual Constructor to build the WebClient
    public AnalysisServiceImpl(WebClient.Builder webClientBuilder,
                               ResumeRepository resumeRepository,
                               AnalysisResultRepository analysisResultRepository) {
        this.webClient = webClientBuilder.build();
        this.resumeRepository = resumeRepository;
        this.analysisResultRepository = analysisResultRepository;
    }

    @Value("${gemini.api.key}")
    private String geminiApiKey;

    @Value("${gemini.api.url}")
    private String geminiApiUrl;

    @Override
    public String analyzeResume(Long resumeId, String jobDescription) {
        var resume = resumeRepository.findById(resumeId)
                .orElseThrow(() -> new RuntimeException("Resume not found"));

        String prompt = "Analyze the following resume against the job description.\n" +
                "Give a matching score out of 100, and list missing skills.\n\n" +
                "Job Description:\n" + jobDescription + "\n\n" +
                "Resume Text:\n" + resume.getExtractedText();

        GeminiRequest.Part part = new GeminiRequest.Part(prompt);
        GeminiRequest.Content content = new GeminiRequest.Content(List.of(part));
        GeminiRequest request = new GeminiRequest(List.of(content));

        GeminiResponse response = webClient.post()              // 1. We start the mailman
                .uri(geminiApiUrl.trim() + "?key=" + geminiApiKey.trim()) // 2. The destination address (FIXED: trimmed hidden characters)
                .header(org.springframework.http.HttpHeaders.CONTENT_TYPE, org.springframework.http.MediaType.APPLICATION_JSON_VALUE) // 3. The Label (FIXED: added JSON header)
                .bodyValue(request)                              // 4. HERE IS WHAT WE SEND! We hand the mailman the GeminiRequest box!
                .retrieve()                                         // 5. We tell the mailman to wait for the reply...
                .bodyToMono(GeminiResponse.class)                       // 6. When the reply comes, put the data inside our empty GeminiResponse box.
                .block(); // asynchronous. "Pause the Java program here and wait until the HTTP response comes back from Google before moving to the next line"
                          // comes back from Google before moving to the next line"

        String aiScore = response.getCandidates().getFirst().getContent().getParts().getFirst().getText();
        AnalysisResult analysisResult = AnalysisResult.builder()
                .resumeId(resume.getId())
                .jobDescription(jobDescription)
                .suggestions(aiScore)
                .build();
        analysisResultRepository.save(analysisResult);

        return aiScore;
    }
}
