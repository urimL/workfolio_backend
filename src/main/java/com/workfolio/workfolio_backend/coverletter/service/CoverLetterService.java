package com.workfolio.workfolio_backend.coverletter.service;

import com.workfolio.workfolio_backend.coverletter.domain.CoverLetter;
import com.workfolio.workfolio_backend.coverletter.dto.CoverLetterDto.AddCoverLetterRequest;
import com.workfolio.workfolio_backend.coverletter.dto.CoverLetterDto.UpdateCoverLetterRequest;
import com.workfolio.workfolio_backend.coverletter.repository.CoverLetterRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class CoverLetterService {

    private final CoverLetterRepository coverLetterRepository;

    /** 자기소개서 추가 */
    public CoverLetter save(AddCoverLetterRequest request, String nickname, String email) {
        String userName = SecurityContextHolder.getContext().getAuthentication().getName();
        if (!email.equals(userName)) {
            throw new IllegalArgumentException("Unauthorized User");
        }
        return coverLetterRepository.save(request.toEntity(nickname, email));
    }

    /** 전체 목록 조회 */
    public List<CoverLetter> findAllByEmail(String email) {
        String userName = SecurityContextHolder.getContext().getAuthentication().getName();
        if (!email.equals(userName)) {
            throw new IllegalArgumentException("Unauthorized User");
        }

        return coverLetterRepository.findAllByUserEmail(email);
    }

    /** 상세 조회 */
    public Optional<CoverLetter> findById(Long id) {
        CoverLetter coverLetter = coverLetterRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("not found interview id : " + id));

        authorizeArticleAuthor(coverLetter);

        return coverLetterRepository.findById(id);
    }

    /** 자기소개서 삭제 */
    public void delete(Long id) {

        CoverLetter coverLetter = coverLetterRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("not found interview id : " + id));

        authorizeArticleAuthor(coverLetter);
        coverLetterRepository.deleteById(id);

    }

    /** 자기소개서 아이디로 작성자 찾기 */
    public String findUserEmailById(Long id) {
        String email = coverLetterRepository.findById(id).get().getUserEmail();
        return email;
    }

    /** 자기소개서 수정 */
    @Transactional
    public CoverLetter update(long id, UpdateCoverLetterRequest req) {
        CoverLetter coverLetter = coverLetterRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("not found interview id : " + id));

        authorizeArticleAuthor(coverLetter);
        coverLetter.update(req.getCp(), req.getObjective());

        return coverLetter;
    }

    /** 작성자가 현재 로그인 된 유저가 맞는지 확인 */
    public static void authorizeArticleAuthor(CoverLetter coverLetter) {
        String userName = SecurityContextHolder.getContext().getAuthentication().getName();
        if (!coverLetter.getUserEmail().equals(userName)) {
            throw new IllegalArgumentException("Not Authorized user");
        }
    }
}
