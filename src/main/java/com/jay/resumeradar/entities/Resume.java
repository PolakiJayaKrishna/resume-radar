package com.jay.resumeradar.entities;

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
    @Column(name = "uploaded_at", nullable = false, updatable = false)
    private LocalDateTime uploadedAt;
}
