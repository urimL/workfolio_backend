package com.workfolio.workfolio_backend.portfolio.certificate.service;

import com.workfolio.workfolio_backend.portfolio.certificate.domain.Certificate;
import com.workfolio.workfolio_backend.portfolio.certificate.dto.AddCertificateRequest;
import com.workfolio.workfolio_backend.portfolio.certificate.dto.UpdateCertificateRequest;
import com.workfolio.workfolio_backend.portfolio.certificate.repository.CertificateRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class CertificateService {
    private final CertificateRepository certificateRepository;

    //자격증 추가
    public Certificate save(AddCertificateRequest request, String email) {
        return certificateRepository.save(request.toEntity(email));
    }

    //전체 목록 조회
    public List<Certificate> findAllByEmail(String email) {
        return certificateRepository.findAllByUserEmail(email);
    }

    //자격증 삭제
    public void delete(Long id) {
        certificateRepository.deleteById(id);
    }

    //자격증 수정
    @Transactional
    public UpdateCertificateRequest update(long id, UpdateCertificateRequest request) {
        Certificate certificate = certificateRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("not found certificate id: "+id));

        authorizeArticleAuthor(certificate);
        certificate.update(request.getName(), request.getDate(), request.getCert_num(), request.getAgency());

        return new UpdateCertificateRequest(request.getName(), request.getDate(), request.getCert_num(), request.getAgency());
    }

    // 작성자가 현재 로그인 된 유저가 맞는지 확인
    public static void authorizeArticleAuthor(Certificate certificate) {
        String userName = SecurityContextHolder.getContext().getAuthentication().getName();
        if (!certificate.getUserEmail().equals(userName)) {
            throw new IllegalArgumentException("Not Authorized user");
        }
    }

    public Certificate findById(Long id) {
        return certificateRepository.findById(id)
                .orElseThrow(()->new IllegalArgumentException("Not found Certificate id : " + id));
    }
}

