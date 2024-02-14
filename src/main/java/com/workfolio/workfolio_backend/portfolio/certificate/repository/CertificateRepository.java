package com.workfolio.workfolio_backend.portfolio.certificate.repository;

import com.workfolio.workfolio_backend.portfolio.certificate.domain.Certificate;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CertificateRepository extends JpaRepository<Certificate, Long> {
    List<Certificate> findAllByUserEmail(String userEmail);
}
