package com.workfolio.workfolio_backend.user.repository;

import com.workfolio.workfolio_backend.interview.domain.Interview;
import com.workfolio.workfolio_backend.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
}