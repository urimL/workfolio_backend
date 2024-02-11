package com.workfolio.workfolio_backend.covoerletter.controller;

import com.workfolio.workfolio_backend.covoerletter.domain.CoverLetter;
import com.workfolio.workfolio_backend.covoerletter.dto.AddCoverLetterRequest;
import com.workfolio.workfolio_backend.covoerletter.dto.CoverLetterResponse;
import com.workfolio.workfolio_backend.covoerletter.dto.UpdateCoverLetterRequest;
import com.workfolio.workfolio_backend.covoerletter.service.CoverLetterService;
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
            coverLetterService.delete(id);
            /** TODO : 문항 삭제 */

        } else {
            throw new IllegalArgumentException("Not Authorized User");
        }
        return ResponseEntity.ok().build();
    }

    //전체 목록 보기 (cp만)
    @GetMapping("/coverletter")
    public ResponseEntity<List<CoverLetterResponse>> findAllCoverLetter(Principal principal) {
        String email = principal.getName();
        List<CoverLetterResponse> coverLetters = coverLetterService.findAllByEmail(email)
                .stream()
                .map(CoverLetterResponse::new)
                .toList();

        return ResponseEntity.ok()
                .body(coverLetters);
    }

}
