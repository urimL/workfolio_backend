package com.workfolio.workfolio_backend.interview.service;

import com.workfolio.workfolio_backend.interview.domain.Interview;
import com.workfolio.workfolio_backend.interview.dto.AddInterviewRequest;
import com.workfolio.workfolio_backend.interview.dto.UpdateInterviewRequest;
import com.workfolio.workfolio_backend.interview.repository.InterviewRepository;
import com.workfolio.workfolio_backend.user.domain.User;
import com.workfolio.workfolio_backend.user.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Transient;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.List;
import java.util.Optional;


@RequiredArgsConstructor
@Service
public class InterviewService {

    private final InterviewRepository interviewRepository;
    private static UserRepository userRepository;

    /** 면접 질문 추가 */
    public Interview save(AddInterviewRequest request, String email) {
        return interviewRepository.save(request.toEntity(email));
    }

    /** 전체 목록 조회 */
    public List<Interview> findAllByEmail(String email) {
        return interviewRepository.findAllByUserEmail(email);
    }

    /** 질문 삭제 */
    public void delete(Long id) {
        interviewRepository.deleteById(id);
    }

    /** 면접 아이디로 작성자 찾기 */
    public String findUserEmailById(Long id) {
        String email = interviewRepository.findById(id).get().getUserEmail();
        return email;
    }

    /** 질문 수정 */
    @Transactional
    public Interview update(long id, UpdateInterviewRequest req) {
        Interview interview = interviewRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("not found interview id : " + id));

        authorizeArticleAuthor(interview);
        interview.update(req.getQuestion(), req.getAnswer(), req.getCp());

        return interview;
    }

    /** 작성자가 현재 로그인 된 유저가 맞는지 확인 */
    public static void authorizeArticleAuthor(Interview interview) {
        String userName = SecurityContextHolder.getContext().getAuthentication().getName();
        System.out.println(userName);
        if (!interview.getUserEmail().equals(userName)) {
            throw new IllegalArgumentException("Not Authorized user");
        }
    }
}
