package com.jay.resumeradar.service;

import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;

public interface ResumeService {
    String extractTextFromPdf(MultipartFile file) throws IOException;
}
