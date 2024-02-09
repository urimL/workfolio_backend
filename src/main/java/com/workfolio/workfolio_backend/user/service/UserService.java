package com.workfolio.workfolio_backend.user.service;

import com.workfolio.workfolio_backend.user.domain.User;
import com.workfolio.workfolio_backend.user.dto.UpdateUserRequest;
import com.workfolio.workfolio_backend.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;

    public User findById(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("Unexpected user"));
    }

    public User findByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("Unexpected user"));
    }

    public User update(String email, UpdateUserRequest request) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("not found user"));

        String userName = SecurityContextHolder.getContext().getAuthentication().getName();
        if (!userName.equals(user.getEmail())) {
            throw new IllegalArgumentException("Not Authorized user");
        }

        user.updateNickname(request.getNickname());
        return user;
    }
}
