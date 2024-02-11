package com.workfolio.workfolio_backend.coverletter.repository;

import com.workfolio.workfolio_backend.coverletter.domain.Question;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface QuestionRepository extends JpaRepository<Question, Long> {
    List<Question> findAllByClId(Long clid);
    void deleteAllByClId(long clId);
}
