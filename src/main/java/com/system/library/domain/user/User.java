package com.system.library.domain.user;

import com.system.library.domain.auditing.BaseTimeEntity;
import com.system.library.domain.converter.StringToUuidConverter;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.GenericGenerator;

import java.util.UUID;

@Entity
@Table(name = "users")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@DynamicUpdate
public class User extends BaseTimeEntity {
  @Id
  @GeneratedValue(generator = "user")
  @GenericGenerator(name = "user", strategy = "org.hibernate.id.UUIDGenerator")
  @Column(name = "user_id", columnDefinition = "BINARY(16) DEFAULT (UNHEX(REPLACE(UUID(), \"-\", \"\")))")
  @Convert(converter = StringToUuidConverter.class)
  private UUID id;

  @Column(nullable = false, unique = true)
  @NotBlank(message = "닉네임은 공백일 수 없어요")
  private String nickname;

  @Column(nullable = false, unique = true)
  @Email(message = "메일 형식에 맞춰 작성해주세요",
          regexp = "^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$")
  @NotEmpty(message = "메일 형식에 맞춰 작성해주세요")
  private String email;

  @Setter
  private String password;

  @Enumerated(EnumType.STRING)
  private UserRole role;
}