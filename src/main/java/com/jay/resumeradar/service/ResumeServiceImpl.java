package com.jay.resumeradar.service;

import com.jay.resumeradar.entities.Resume;
import com.jay.resumeradar.repository.ResumeRepository;
import lombok.RequiredArgsConstructor;
import com.jay.resumeradar.entities.User;
import org.apache.pdfbox.Loader;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class ResumeServiceImpl implements ResumeService{

    private static final Logger log = LoggerFactory.getLogger(ResumeServiceImpl.class);

    private final ResumeRepository resumeRepository;
    @Override
    public String extractTextFromPdf(MultipartFile file) throws IOException {

        String extractText;

        log.info("PDF text extracted from file: {}", file.getOriginalFilename());

        // 1A. Load the PDF document from the file's input stream
        try(PDDocument document = Loader.loadPDF(file.getBytes())){ //Java allocates a big chunk of your RAM (memory) to hold that document.

            // 2. Create a "Stripper" to extract text
            PDFTextStripper stripper = new PDFTextStripper();

            // 3. Extract and return the text
            extractText =  stripper.getText(document);
        }

        // 1B. Get the currently logged-in User
        User currentUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        Resume resume = new Resume();
        resume.setUserId(currentUser.getId());
        resume.setFileName(file.getOriginalFilename());
        resume.setExtractedText(extractText);
        resume.setUploadedAt(LocalDateTime.now());

        resumeRepository.save(resume);
        log.info("Resume saved for userId: {}, file: {}", currentUser.getId(), file.getOriginalFilename());

        return extractText;
    }
}
