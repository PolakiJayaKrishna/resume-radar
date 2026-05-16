package com.jay.resumeradar.entities;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "resumes")
@NoArgsConstructor
@AllArgsConstructor
public class Resume{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long userId; //

    private String fileName;

    @Lob  // stores as TEXT/LONGTEXT in MySQL. No limit.
    private String extractedText;

    @JsonFormat(pattern = "dd-MM-yyyy HH:mm:ss")
    @Column(name = "uploaded_at", nullable = false, updatable = false)
    private LocalDateTime uploadedAt;
}
