package com.workfolio.workfolio_backend.coverletter.controller;

import com.workfolio.workfolio_backend.coverletter.domain.CoverLetter;
import com.workfolio.workfolio_backend.coverletter.dto.CoverLetterDto.AddCoverLetterRequest;
import com.workfolio.workfolio_backend.coverletter.dto.CoverLetterDto.CoverLetterListResponse;
import com.workfolio.workfolio_backend.coverletter.dto.CoverLetterDto.UpdateCoverLetterRequest;
import com.workfolio.workfolio_backend.coverletter.service.CoverLetterService;
import com.workfolio.workfolio_backend.coverletter.service.QuestionService;
import com.workfolio.workfolio_backend.user.domain.User;
import com.workfolio.workfolio_backend.user.repository.UserRepository;
import com.workfolio.workfolio_backend.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RequiredArgsConstructor
@RestController
public class CoverLetterController {

    private final CoverLetterService coverLetterService;
    private final QuestionService questionService;
    private final UserService userService;
    private final UserRepository userRepository;

    //자기소개서 추가
    @PostMapping("/coverletter")
    public ResponseEntity<CoverLetter> addCoverLetter(@RequestBody AddCoverLetterRequest request, Principal principal) {
        User user = userService.findByEmail(principal.getName());
        String nickname = userRepository.findByEmail(principal.getName()).get().getNickname();
        CoverLetter savedCoverLetter = coverLetterService.save(request, nickname, principal.getName());

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(savedCoverLetter);
    }

    //자기소개서 수정 (cp, objective)
    @PutMapping("coverletter/{id}")
    public ResponseEntity<CoverLetter> updateCoverLetter(@PathVariable Long id, @RequestBody UpdateCoverLetterRequest request) {
        CoverLetter updated = coverLetterService.update(id, request);
        return ResponseEntity.ok().body(updated);
    }

    //자기소개서 삭제
    @DeleteMapping("/coverletter/{id}")
    public ResponseEntity<Void> deleteCoverLetter(@PathVariable long id, Principal principal) {
        if (principal.getName().equals(coverLetterService.findUserEmailById(id))) {
            questionService.deleteAllByClID(id);
            coverLetterService.delete(id);

        } else {
            throw new IllegalArgumentException("Not Authorized User");
        }
        return ResponseEntity.ok().build();
    }

    //전체 목록 보기 (cp/objective)
    @GetMapping("/coverletter")
    public ResponseEntity<List<CoverLetterListResponse>> findAllCoverLetter(Principal principal) {
        String email = principal.getName();
        List<CoverLetterListResponse> coverLetters = coverLetterService.findAllByEmail(email)
                .stream()
                .map(CoverLetterListResponse::new)
                .toList();

        return ResponseEntity.ok()
                .body(coverLetters);
    }

}
