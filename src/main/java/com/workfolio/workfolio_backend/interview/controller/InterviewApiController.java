package com.workfolio.workfolio_backend.interview.controller;

import com.workfolio.workfolio_backend.interview.domain.Interview;
import com.workfolio.workfolio_backend.interview.dto.AddInterviewRequest;
import com.workfolio.workfolio_backend.interview.dto.InterviewResponse;
import com.workfolio.workfolio_backend.interview.dto.UpdateInterviewRequest;
import com.workfolio.workfolio_backend.interview.service.InterviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RequiredArgsConstructor
@RestController
public class InterviewApiController {
    private final InterviewService interviewService;

    /** 면접 질문 추가 */
    @PostMapping("/interview/question")
    public ResponseEntity<Interview> addQuestion(@RequestBody AddInterviewRequest request, Principal principal) {
        String email = principal.getName();
        Interview savedInterview = interviewService.save(request, email);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(savedInterview);
    }

    /** 면접 질문 전체 목록 조회 */
    @GetMapping("/interview/question")
    public ResponseEntity<List<InterviewResponse>> findAll(Principal principal) {
        String email = principal.getName();
        List<InterviewResponse> interviews = interviewService.findAllByEmail(email)
                .stream()
                .map(InterviewResponse::new)
                .toList();

        return ResponseEntity.ok()
                .body(interviews);
    }

    /** 면접 질문 삭제 */
    @DeleteMapping("/interview/question/{id}")
    public ResponseEntity<Void> deleteInterview(@PathVariable long id, Principal principal) {
        if (principal.getName().equals(interviewService.findUserEmailById(id))) {
            interviewService.delete(id);
        } else {
            throw new IllegalArgumentException("Not Authorized User");
        }

        return ResponseEntity.ok().build();
    }

    /** 면접 질문 수정 */
    @PutMapping("/interview/question/{id}")
    public ResponseEntity<Interview> updateInterview(@PathVariable long id, @RequestBody UpdateInterviewRequest req) {
        Interview updated = interviewService.update(id, req);

        return ResponseEntity.ok().body(updated);
    }
}
