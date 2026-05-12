package com.jay.resumeradar.repository;

import com.jay.resumeradar.entities.AnalysisResult;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AnalysisResultRepository extends JpaRepository<AnalysisResult , Long> {
}
