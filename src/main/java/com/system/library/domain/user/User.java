package com.system.library.domain.user;

import com.system.library.domain.auditing.BaseTimeEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;

@Entity
@Getter
public class User extends BaseTimeEntity {
  @Id
  @Column(name = "user_id")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false, unique = true)
  @NotBlank(message = "닉네임은 공백일 수 없어요")
  private String nickname;

  @Column(nullable = false, unique = true)
  @Email(message = "메일 형식에 맞춰 작성해주세요",
          regexp = "^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$")
  @NotEmpty(message = "메일 형식에 맞춰 작성해주세요")
  private String email;

  private String password;
}