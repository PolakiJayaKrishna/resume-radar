package com.jay.resumeradar.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jay.resumeradar.entities.AnalysisResult;
import com.jay.resumeradar.entities.AnalysisStatus;
import com.jay.resumeradar.repository.AnalysisResultRepository;
import com.jay.resumeradar.repository.ResumeRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.reactive.function.client.WebClient;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
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
        
    }
}