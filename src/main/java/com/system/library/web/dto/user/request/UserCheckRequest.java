package com.system.library.web.dto.user.request;

import com.system.library.domain.user.User;
import com.system.library.domain.user.UserRole;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserCheckRequest {
  private String nickname;
  private String email;
  private String password;

  public User toEntity() {
    return User.builder()
            .nickname(nickname)
            .email(email)
            .password(password)
            .role(UserRole.ROLE_USER)
            .build();
  }
}
