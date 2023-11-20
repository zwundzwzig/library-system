package com.system.library.domain.user;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum UserRole {
  ROLE_ADMIN("ADMIN", "관리자"),
  ROLE_USER("USER", "일반사용자");

  private final String key;
  private final String title;
}
