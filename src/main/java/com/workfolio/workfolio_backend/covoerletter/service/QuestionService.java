package com.workfolio.workfolio_backend.covoerletter.service;

import com.workfolio.workfolio_backend.covoerletter.domain.CoverLetter;
import com.workfolio.workfolio_backend.covoerletter.domain.Question;
import com.workfolio.workfolio_backend.covoerletter.dto.AddQuestionRequest;
import com.workfolio.workfolio_backend.covoerletter.dto.UpdateQuestionRequest;
import com.workfolio.workfolio_backend.covoerletter.repository.CoverLetterRepository;
import com.workfolio.workfolio_backend.covoerletter.repository.QuestionRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class QuestionService {

    private final QuestionRepository questionRepository;
    private final CoverLetterRepository coverLetterRepository;

    //전체 목록 조회
    public List<Question> findAllByClId(Long cl_id) {
        return questionRepository.findAllByClId(cl_id);
    }

    //문항 추가
    public Question save(AddQuestionRequest request, Long cl_id) {
        return questionRepository.save(request.toEntity(cl_id));
    }

    //문항 수정
    @Transactional
    public Question update(long id, UpdateQuestionRequest request) {
        Question question = questionRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("not found question id : "+id));

        question.update(request.getQuestion(), request.getAnswer());
        return question;
    }

    //문항 삭제
    public void delete(Long id) {
        questionRepository.deleteById(id);
    }

}
