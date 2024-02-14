package com.workfolio.workfolio_backend.portfolio.grade.repository;

import com.workfolio.workfolio_backend.portfolio.grade.domain.Grade;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GradeRepository extends JpaRepository<Grade, Long> {
    Grade findByUserEmail(String email);
}
