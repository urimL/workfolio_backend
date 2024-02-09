package com.workfolio.workfolio_backend.user;

import com.workfolio.workfolio_backend.user.domain.User;
import com.workfolio.workfolio_backend.user.dto.UpdateUserRequest;
import com.workfolio.workfolio_backend.user.dto.UserResponseDto;
import com.workfolio.workfolio_backend.user.dto.UserViewResponse;
import com.workfolio.workfolio_backend.user.service.UserService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RequiredArgsConstructor
@RestController
public class UserController {
    private final UserService userService;

    /** 사용자 정보 보기 */
    @GetMapping("/my/info")
    public ResponseEntity<UserViewResponse> userInfo(Principal principal) {
        User user = userService.findByEmail(principal.getName());

        return ResponseEntity.ok()
                .body(new UserViewResponse(user));
    }

    /** 사용자 닉네임 변경 */
    @PutMapping("/my/info")
    public ResponseEntity<User> updateNickname(@RequestBody UpdateUserRequest request, Principal principal) {
        User updatedUser = userService.update(principal.getName(), request);
        return ResponseEntity.ok().body(updatedUser);
    }

}
