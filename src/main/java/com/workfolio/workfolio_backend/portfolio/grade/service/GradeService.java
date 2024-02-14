package com.workfolio.workfolio_backend.portfolio.grade.service;

import com.workfolio.workfolio_backend.portfolio.grade.domain.Grade;
import com.workfolio.workfolio_backend.portfolio.grade.dto.AddGradeRequest;
import com.workfolio.workfolio_backend.portfolio.grade.dto.UpdateGradeRequest;
import com.workfolio.workfolio_backend.portfolio.grade.repository.GradeRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class GradeService {

    private final GradeRepository gradeRepository;

    //학점 추가
    public Grade save(AddGradeRequest request, String email) {
        return gradeRepository.save(request.toEntity(email));
    }

    //학점 수정
    @Transactional
    public UpdateGradeRequest update(String email, UpdateGradeRequest request) {
        Long gradeId = gradeRepository.findByUserEmail(email).getId();
        Grade grade = gradeRepository.findById(gradeId)
                .orElseThrow(() -> new IllegalArgumentException("not found grade id of user " + email));
        authorizeArticleAuthor(grade);
        grade.update(request.getTotalGrade(), request.getMajorGrade());

        return new UpdateGradeRequest(request.getTotalGrade(), request.getMajorGrade());
    }

    // 작성자가 현재 로그인 된 유저가 맞는지 확인
    public static void authorizeArticleAuthor(Grade grade) {
        String userName = SecurityContextHolder.getContext().getAuthentication().getName();
        if (!grade.getUserEmail().equals(userName)) {
            throw new IllegalArgumentException("Not Authorized user");
        }
    }

    //userEmail로 학점 찾기
    public Grade findByUserEmail(String email) {
        Grade grade = gradeRepository.findByUserEmail(email);
        return grade;
    }
}
