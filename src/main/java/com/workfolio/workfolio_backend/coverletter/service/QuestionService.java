package com.workfolio.workfolio_backend.coverletter.service;

import com.workfolio.workfolio_backend.coverletter.domain.Question;
import com.workfolio.workfolio_backend.coverletter.dto.QuestionDto.AddQuestionRequest;
import com.workfolio.workfolio_backend.coverletter.dto.QuestionDto.UpdateQuestionRequest;
import com.workfolio.workfolio_backend.coverletter.repository.CoverLetterRepository;
import com.workfolio.workfolio_backend.coverletter.repository.QuestionRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class QuestionService {

    private final QuestionRepository questionRepository;

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
    @Transactional
    public void delete(Long id) {
        questionRepository.deleteById(id);
    }

    //문항 전체 삭제
    @Transactional
    public void deleteAllByClID(long cl_id) {
        questionRepository.deleteAllByClId(cl_id);
    }
}
