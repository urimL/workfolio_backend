package com.workfolio.workfolio_backend.coverletter.controller;

import com.workfolio.workfolio_backend.coverletter.domain.CoverLetter;
import com.workfolio.workfolio_backend.coverletter.domain.Question;
import com.workfolio.workfolio_backend.coverletter.dto.CoverLetterDto.CoverLetterDetailResponse;
import com.workfolio.workfolio_backend.coverletter.dto.QuestionDto.AddQuestionRequest;
import com.workfolio.workfolio_backend.coverletter.dto.QuestionDto.QuestionResponse;
import com.workfolio.workfolio_backend.coverletter.dto.QuestionDto.UpdateQuestionRequest;
import com.workfolio.workfolio_backend.coverletter.service.CoverLetterService;
import com.workfolio.workfolio_backend.coverletter.service.QuestionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RequiredArgsConstructor
@RestController
public class QuestionController {

    private final QuestionService questionService;
    private final CoverLetterService coverLetterService;

    //문항 주가
    @PostMapping("/coverletter/{cl_id}")
    public ResponseEntity<Question> addQuestion(@RequestBody AddQuestionRequest request, @PathVariable long cl_id, Principal principal) {
        Question savedQuestion = questionService.save(request, cl_id);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(savedQuestion);
    }

    //문항 수정 (질문, 답변)
    @PutMapping("/coverletter/{cl_id}/{q_id}")
    public ResponseEntity<Question> updateQuestion(@PathVariable Long q_id, @RequestBody UpdateQuestionRequest request) {
        Question updatedQuestion = questionService.update(q_id, request);
        return ResponseEntity.ok().body(updatedQuestion);
    }

    //문항 삭제
    @DeleteMapping("/coverletter/{cl_id}/{q_id}")
    public ResponseEntity<Void> deleteQuestion(@PathVariable long q_id) {
        questionService.delete(q_id);
        return ResponseEntity.ok().build();
    }

    //하나의 cp_id에 대한 전체 문항 보기
    @GetMapping("/coverletter/{cl_id}")
    public ResponseEntity<CoverLetterDetailResponse> findAllQuestion(@PathVariable Long cl_id) {
        List<QuestionResponse> questions = questionService.findAllByClId(cl_id)
                .stream()
                .map(QuestionResponse::new)
                .toList();

        CoverLetter coverLetter = coverLetterService.findById(cl_id).get();

        return ResponseEntity.ok().body(new CoverLetterDetailResponse(coverLetter.getCp(), coverLetter.getObjective(), questions));
    }

}
