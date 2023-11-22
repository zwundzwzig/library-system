package com.system.library.service;

import com.system.library.domain.user.User;
import com.system.library.domain.user.UserRepository;
import com.system.library.web.dto.user.request.UserCheckRequest;
import com.system.library.web.dto.user.response.UserNicknameResponse;
import com.system.library.web.dto.user.response.UserSimpleCheckResponse;
import jakarta.persistence.NoResultException;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService {
  private final UserRepository userRepository;

  @Transactional(readOnly = true)
  public UserSimpleCheckResponse isNicknameExists(String nickname) {
    Optional<User> user = userRepository.findByNickname(nickname);
    user.orElseThrow(() -> new NoResultException("해당 닉네임을 가진 사용자는 없어요"));
    return new UserSimpleCheckResponse(user.isPresent());
  }

  @Transactional(readOnly = true)
  public User findById(String id) {
    return userRepository.findById(UUID.fromString(id)).orElseThrow(() -> new NoResultException("존재하지 않는 사용자에요"));
  }

  @Transactional
  public UserNicknameResponse signUp(UserCheckRequest request) {
    if (userRepository.findByEmail(request.getEmail()).isPresent()) {
      throw new DataIntegrityViolationException("이미 존재하는 이메일입니다.");
    }

    if (userRepository.findByNickname(request.getNickname()).isPresent()) {
      throw new DataIntegrityViolationException("이미 존재하는 닉네임입니다.");
    }

    userRepository.save(request.toEntity());
    return new UserNicknameResponse(request.getNickname());
  }

  @Transactional(readOnly = true)
  public HttpHeaders signIn(UserCheckRequest request) {
    Optional<User> user = userRepository.findByEmail(request.getEmail());

    if (user.isEmpty()) throw new NoResultException("회원가입이 필요합니다.");
    if (!user.get().getPassword().equals(request.getPassword())) throw new RuntimeException("정확한 비밀번호를 입력해주세요");

    String id = user.get().getId().toString();
    return setTokenHeaders(id);
  }

  public void signOut(String id) {
    HttpHeaders headers = new HttpHeaders();
    findById(id);
    headers.remove("Authorization");
  }

  private HttpHeaders setTokenHeaders(String id) {
    HttpHeaders headers = new HttpHeaders();
    headers.add("Authorization", "Bearer " + id);
    return headers;
  }

}
