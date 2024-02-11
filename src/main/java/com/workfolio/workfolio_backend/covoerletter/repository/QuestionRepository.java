package com.workfolio.workfolio_backend.covoerletter.repository;

import com.workfolio.workfolio_backend.covoerletter.domain.Question;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface QuestionRepository extends JpaRepository<Question, Long> {
    List<Question> findAllByClId(Long clid);
}
