package com.workfolio.workfolio_backend.grade.repository;

import com.workfolio.workfolio_backend.grade.domain.Grade;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GradeRepository extends JpaRepository<Grade, Long> {
    Grade findByUserEmail(String email);
}
