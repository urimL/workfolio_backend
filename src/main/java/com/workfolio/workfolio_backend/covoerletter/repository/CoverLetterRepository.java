package com.workfolio.workfolio_backend.covoerletter.repository;

import com.workfolio.workfolio_backend.covoerletter.domain.CoverLetter;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CoverLetterRepository extends JpaRepository<CoverLetter, Long> {
    List<CoverLetter> findAllByUserEmail(String email);
    List<CoverLetter> findAllByCp(String cp);
    List<CoverLetter> findAllByCpAndObjective(String cp, String objective);
}
