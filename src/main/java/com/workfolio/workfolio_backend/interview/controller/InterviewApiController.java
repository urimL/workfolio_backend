package com.workfolio.workfolio_backend.interview.controller;

import com.workfolio.workfolio_backend.interview.domain.Interview;
import com.workfolio.workfolio_backend.interview.dto.AddInterviewRequest;
import com.workfolio.workfolio_backend.interview.dto.InterviewResponse;
import com.workfolio.workfolio_backend.interview.dto.UpdateInterviewRequest;
import com.workfolio.workfolio_backend.interview.service.InterviewService;
import com.workfolio.workfolio_backend.user.domain.User;
import com.workfolio.workfolio_backend.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.hibernate.sql.Update;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RequiredArgsConstructor
@RestController
public class InterviewApiController {
    private final InterviewService interviewService;
    private final UserService userService;


    /**
     * 면접 질문 추가
     */
    @PostMapping("/interviews")
    public ResponseEntity<AddInterviewRequest> addQuestion(@RequestBody AddInterviewRequest request, Principal principal) {
        User user = userService.findByEmail(principal.getName());

        Interview savedInterview = interviewService.save(request, user.getNickname(), user.getEmail());

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new AddInterviewRequest(savedInterview.getCp(), savedInterview.getQuestion(), savedInterview.getAnswer()));
    }

    /** 면접 질문 전체 목록 조회 */
    @GetMapping("/interviews/questions")
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
    @DeleteMapping("/interviews/questions/{id}")
    public ResponseEntity<Void> deleteInterview(@PathVariable long id, Principal principal) {
        if (principal.getName().equals(interviewService.findUserEmailById(id))) {
            interviewService.delete(id);
        } else {
            throw new IllegalArgumentException("Cannot delete : Not Authorized User");
        }

        return ResponseEntity.ok().build();
    }

    /** 면접 질문 수정 */
    @PutMapping("/interviews/questions/{id}")
    public ResponseEntity<UpdateInterviewRequest> updateInterview(@PathVariable long id, @RequestBody UpdateInterviewRequest req) {
        UpdateInterviewRequest updated = interviewService.update(id, req);

        return ResponseEntity.ok().body(updated);
    }
}
