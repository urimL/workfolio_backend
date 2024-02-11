package com.workfolio.workfolio_backend.coverletter.repository;

import com.workfolio.workfolio_backend.coverletter.domain.CoverLetter;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CoverLetterRepository extends JpaRepository<CoverLetter, Long> {
    List<CoverLetter> findAllByUserEmail(String email);
}
