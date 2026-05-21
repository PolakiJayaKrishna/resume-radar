package com.jay.resumeradar.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jay.resumeradar.dto.GeminiAnalysisDto;
import com.jay.resumeradar.dto.GeminiRequest;
import com.jay.resumeradar.dto.GeminiResponse;
import com.jay.resumeradar.entities.AnalysisResult;
import com.jay.resumeradar.entities.AnalysisStatus;
import com.jay.resumeradar.repository.AnalysisResultRepository;
import com.jay.resumeradar.repository.ResumeRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Service
public class AnalysisServiceImpl implements AnalysisService {

    private final WebClient webClient;
    private final ResumeRepository resumeRepository;
    private final AnalysisResultRepository analysisResultRepository;
    private final ObjectMapper objectMapper;
    private final ExecutorService executorService = Executors.newVirtualThreadPerTaskExecutor();

    // Manual Constructor to build the WebClient
    public AnalysisServiceImpl(WebClient.Builder webClientBuilder,
                               ResumeRepository resumeRepository,
                               AnalysisResultRepository analysisResultRepository, ObjectMapper objectMapper) {
        this.webClient = webClientBuilder.build();
        this.resumeRepository = resumeRepository;
        this.analysisResultRepository = analysisResultRepository;
        this.objectMapper = objectMapper;
    }

    @Value("${gemini.api.key}")
    private String geminiApiKey;

    @Value("${gemini.api.url}")
    private String geminiApiUrl;

    @Override
    public Long analyzeResume(Long resumeId, String jobDescription) {
        var resume = resumeRepository.findById(resumeId)
                .orElseThrow(() -> new RuntimeException("Resume not found"));

        AnalysisResult pending = AnalysisResult.builder()
                .resumeId(resumeId)
                .jobDescription(jobDescription)
                .status(AnalysisStatus.PENDING)
                .build();

        final AnalysisResult saved = analysisResultRepository.save(pending);


        executorService.submit(() -> {
            try {
                String prompt =
                        "Analyze the following resume against the job description.\n" +
                                "Give a matching score out of 100, and list missing skills.\n" +
                                "Also identify weak areas in the resume and suggest improvements.\n" +
                                "Keep each item in weakAreas and recommendations short, maximum 1–2 sentences.\n\n" +
                                "Return only valid JSON with the keys matchScore (number), " +
                                "missingKeywords (array of strings), weakAreas (array of strings), " +
                                "recommendations (array of strings), and rewrittenSummary (string). " +
                                "Do not include any text outside the JSON.\n\n" +
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

                // Null check added to prevent NullPointerException
                if (response == null || response.getCandidates() == null || response.getCandidates().isEmpty()) {
                    throw new RuntimeException("Invalid response from Gemini API");
                }

                String aiJson = response.getCandidates().getFirst().getContent().getParts().getFirst().getText();

                if (aiJson.startsWith("```")) {
                    int firstNewLine = aiJson.indexOf('\n');
                    aiJson = aiJson.substring(firstNewLine + 1);
                    int lastBacktick = aiJson.lastIndexOf("```");
                    aiJson = aiJson.substring(0, lastBacktick);
                    aiJson = aiJson.trim();
                }

                //Get the data from the results and assign to the fields.
                GeminiAnalysisDto dto = objectMapper.readValue(aiJson, GeminiAnalysisDto.class);

                saved.setMatchScore(dto.getMatchScore());
                saved.setWeakAreas(objectMapper.writeValueAsString(dto.getWeakAreas()));
                saved.setRecommendations(objectMapper.writeValueAsString(dto.getRecommendations()));
                saved.setRewrittenSummary(objectMapper.writeValueAsString(dto.getRewrittenSummary()));
                saved.setStatus(AnalysisStatus.COMPLETED);
                analysisResultRepository.save(saved);

            }
            catch (Exception e){
                saved.setStatus(AnalysisStatus.FAILED);
                analysisResultRepository.save(saved);
                e.printStackTrace();
            }
        });
        return saved.getId();
    }

    @Override
    public AnalysisResult getResult(Long id) {
        return analysisResultRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Result Not Found"));
    }
}
