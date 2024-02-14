package com.workfolio.workfolio_backend.portfolio.certificate.controller;

import com.workfolio.workfolio_backend.portfolio.certificate.domain.Certificate;
import com.workfolio.workfolio_backend.portfolio.certificate.dto.AddCertificateRequest;
import com.workfolio.workfolio_backend.portfolio.certificate.dto.CertificateResponse;
import com.workfolio.workfolio_backend.portfolio.certificate.dto.UpdateCertificateRequest;
import com.workfolio.workfolio_backend.portfolio.certificate.service.CertificateService;
import com.workfolio.workfolio_backend.user.domain.User;
import com.workfolio.workfolio_backend.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RequiredArgsConstructor
@RestController
public class CertificateApiController {

    private final UserService userService;
    private final CertificateService certificateService;

    //자격증 추가
    @PostMapping("/portfolio/certificate/new")
    public ResponseEntity<AddCertificateRequest> addCertificate(@RequestBody AddCertificateRequest request, Principal principal) {
        User user = userService.findByEmail(principal.getName());
        Certificate certificate = certificateService.save(request, user.getEmail());

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new AddCertificateRequest(request.getName(), request.getDate(), request.getCert_num(), request.getAgency()));
    }

    //자격증 수정
    @PutMapping("/portfolio/certificate/{id}")
    public ResponseEntity<UpdateCertificateRequest> updateCertificate(@RequestBody UpdateCertificateRequest request, @PathVariable("id") Long id) {
        UpdateCertificateRequest updateCertificate = certificateService.update(id, request);
        return ResponseEntity.ok(updateCertificate);
    }

    //자격증 삭제
    @DeleteMapping("/portfolio/certificate/{id}")
    public ResponseEntity<Void> deleteCertificate(@PathVariable("id") long id, Principal principal) {
        if (principal.getName().equals(certificateService.findById(id).getUserEmail())) {
            certificateService.delete(id);
        } else {
            throw new IllegalArgumentException("Cannot delete : Not Authorized User");
        }

        return ResponseEntity.ok().build();
    }

    //전체 조회
    @GetMapping("/portfolio/certificate")
    public ResponseEntity<List<CertificateResponse>> findAll(Principal principal) {
        String email = principal.getName();
        List<CertificateResponse> certificates = certificateService.findAllByEmail(email)
                .stream()
                .map(CertificateResponse::new)
                .toList();

        return ResponseEntity.ok().body(certificates);
    }
}
