package com.workfolio.workfolio_backend.portfolio.grade.controller;

import com.workfolio.workfolio_backend.portfolio.grade.domain.Grade;
import com.workfolio.workfolio_backend.portfolio.grade.dto.AddGradeRequest;
import com.workfolio.workfolio_backend.portfolio.grade.dto.GradeResponse;
import com.workfolio.workfolio_backend.portfolio.grade.dto.UpdateGradeRequest;
import com.workfolio.workfolio_backend.portfolio.grade.service.GradeService;
import com.workfolio.workfolio_backend.user.domain.User;
import com.workfolio.workfolio_backend.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RequiredArgsConstructor
@RestController
public class GradeApiController {

    private final UserService userService;
    private final GradeService gradeService;

    //학점 추가
    @PostMapping("/portfolio/grade/new")
    public ResponseEntity<AddGradeRequest> addGrade(@RequestBody AddGradeRequest request, Principal principal) {
        User user = userService.findByEmail(principal.getName());
        Grade savedGrade = gradeService.save(request, user.getEmail());

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new AddGradeRequest(request.getTotalGrade(), request.getMajorGrade()));
    }

    //학점 수정
    @PutMapping("/portfolio/grade")
    public ResponseEntity<UpdateGradeRequest> updateGrade(@RequestBody UpdateGradeRequest request, Principal principal) {
        UpdateGradeRequest updateGrade = gradeService.update(principal.getName(), request);
        return ResponseEntity.ok(updateGrade);
    }

    //학점 조회 (추후 삭제)
    @GetMapping("/portfolio/grade")
    public ResponseEntity<GradeResponse> gradeView(Principal principal) {
        String email = principal.getName();
        Grade grade = gradeService.findByUserEmail(email);
        User user = userService.findByEmail(email);

        return ResponseEntity.ok(new GradeResponse(grade, user.getNickname()));
    }
}
