package com.workfolio.workfolio_backend.portfolio.certificate.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.workfolio.workfolio_backend.portfolio.certificate.domain.Certificate;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class AddCertificateRequest {
    private String name;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy.MM.dd", timezone = "Asia/Seoul")
    private LocalDate date;
    private String cert_num;
    private String agency;

    public Certificate toEntity(String email) {
        return Certificate.builder()
                .userEmail(email)
                .name(name)
                .date(date)
                .cert_num(cert_num)
                .agency(agency)
                .build();
    }
}
