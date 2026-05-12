package com.jay.resumeradar.repository;

import com.jay.resumeradar.entities.Resume;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ResumeRepository extends JpaRepository<Resume , Long> {
}
