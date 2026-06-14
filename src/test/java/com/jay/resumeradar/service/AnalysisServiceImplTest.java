package com.jay.resumeradar.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jay.resumeradar.entities.AnalysisResult;
import com.jay.resumeradar.entities.AnalysisStatus;
import com.jay.resumeradar.entities.Resume;
import com.jay.resumeradar.exception.ResourceNotFoundException;
import com.jay.resumeradar.repository.AnalysisResultRepository;
import com.jay.resumeradar.repository.ResumeRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.reactive.function.client.WebClient;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
class AnalysisServiceImplTest {

    @Mock
    private AnalysisResultRepository analysisResultRepository;

    @Mock
    private ResumeRepository resumeRepository;

    @Mock
    private ObjectMapper objectMapper;

    @Mock
    private WebClient.Builder webClient;

    @InjectMocks
    private AnalysisServiceImpl analysisService;


    @Test
    void getResult() {
        //Arrange
        Long fakeId = 100L;
        AnalysisResult fakeResult = new AnalysisResult();
        fakeResult.setId(fakeId);
        fakeResult.setStatus(AnalysisStatus.COMPLETED);
        when(analysisResultRepository.findById(fakeId)).thenReturn(java.util.Optional.of(fakeResult));

        //Act
        AnalysisResult actualResult= analysisService.getResult(fakeId);

        //Asset
        assertNotNull(actualResult);
        assertEquals(AnalysisStatus.COMPLETED, actualResult.getStatus());
        assertEquals(fakeId, actualResult.getId());

    }

    @Test
    void getResult_WhenNotFound_ShouldThrowException() {
        //Arrange
        Long fakeId = 999L;
        when(analysisResultRepository.findById(fakeId)).thenReturn(java.util.Optional.empty());


        // --- ACT & ASSERT ---
        assertThrows(ResourceNotFoundException.class, ()-> analysisService.getResult(fakeId));
    }

    @Test
    void analyzeResume_Success(){
        Long fakeId = 2L;
        String fakeJobDescription = "Give me something to write here";
        Resume resume = new Resume();
        resume.setId(fakeId);
        resume.setExtractedText("This is a fake resume PDF text");
        when(resumeRepository.findById(fakeId)).thenReturn(java.util.Optional.of(resume));

        AnalysisResult fakeSavedResult = new AnalysisResult();
        fakeSavedResult.setId(100L);
        when(analysisResultRepository.save(org.mockito.ArgumentMatchers.any())).thenReturn(fakeSavedResult);

        // --- ACT ---
        Long returnedId = analysisService.analyzeResume(fakeId, fakeJobDescription);

        // --- ASSERT ---
        // The method must return the saved record's ID synchronously (before async Gemini call completes)
        assertNotNull(returnedId);
        assertEquals(100L, returnedId);
    }

}