package com.system.library.web;

import com.system.library.service.UserService;
import com.system.library.web.dto.user.request.UserCheckRequest;
import com.system.library.web.dto.user.response.UserNicknameResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {
  private final UserService userService;

  @PostMapping("/sign-up")
  public ResponseEntity<UserNicknameResponse> signUp(@RequestBody UserCheckRequest request) {
    UserNicknameResponse response = userService.signUp(request);
    HttpHeaders headers = userService.signIn(request);
    return ResponseEntity.ok().headers(headers).body(response);
  }

  @PostMapping("/sign-in")
  public ResponseEntity<Void> signIn(@RequestBody UserCheckRequest request) {
    HttpHeaders headers = userService.signIn(request);
    return ResponseEntity.ok().headers(headers).build();
  }

  @GetMapping("/sign-out")
  public ResponseEntity<Void> signOut(@RequestHeader("Authorization") String accessToken) {
    userService.signOut(accessToken);
    return ResponseEntity.ok().build();
  }
}
