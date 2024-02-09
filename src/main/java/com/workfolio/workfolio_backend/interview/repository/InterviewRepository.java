package com.workfolio.workfolio_backend.interview.repository;

import com.workfolio.workfolio_backend.interview.domain.Interview;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface InterviewRepository extends JpaRepository<Interview, Long> {

    //현재 사용자의 면접 질문 리스트 전체보기
    List<Interview> findAllByUserEmail(String userEmail);
}
