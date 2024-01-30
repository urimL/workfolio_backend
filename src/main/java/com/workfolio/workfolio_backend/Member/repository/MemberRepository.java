package com.workfolio.workfolio_backend.Member.repository;

import com.workfolio.workfolio_backend.Member.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {
    Member findByEmailAndPassword(String email, String password);
    Optional<Member> findByEmail(String email);
    boolean existsMemberByEmail(String email);
}
