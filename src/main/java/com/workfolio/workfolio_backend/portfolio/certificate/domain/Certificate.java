package com.workfolio.workfolio_backend.portfolio.certificate.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import jdk.jfr.Timestamp;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Certificate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "userEmail")
    private String userEmail;

    @Column(name = "name")
    private String name;

    @Column(name = "date")
    @Timestamp
    private LocalDate date;

    @Column(name = "cert_num")
    private String cert_num;

    @Column(name = "agency")
    private String agency;

    @Builder
    public Certificate(String userEmail, String name, LocalDate date, String cert_num, String agency) {
        this.userEmail = userEmail;
        this.name = name;
        this.date = date;
        this.cert_num = cert_num;
        this.agency = agency;
    }

    public void update(String name, LocalDate date, String cert_num, String agency) {
        this.name = name;
        this.date = date;
        this.cert_num = cert_num;
        this.agency = agency;
    }
}
