package com.system.library.web;

import com.system.library.service.UserService;
import com.system.library.web.dto.user.request.UserCheckRequest;
import com.system.library.web.dto.user.response.UserNicknameResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {
  private final UserService userService;

  @Operation(summary = "회원가입")
  @ApiResponse(responseCode = "200", description = "Ok")
  @PostMapping("/sign-up")
  public ResponseEntity<UserNicknameResponse> signUp(@RequestBody UserCheckRequest request) {
    UserNicknameResponse response = userService.signUp(request);
    HttpHeaders headers = userService.signIn(request);
    return ResponseEntity.ok().headers(headers).body(response);
  }

  @Operation(summary = "로그인")
  @ApiResponse(responseCode = "201", description = "Created")
  @PostMapping("/sign-in")
  public ResponseEntity<Void> signIn(@RequestBody UserCheckRequest request) {
    HttpHeaders headers = userService.signIn(request);
    return ResponseEntity.ok().headers(headers).build();
  }

  @Operation(summary = "로그아웃")
  @ApiResponse(responseCode = "200", description = "Ok")
  @GetMapping("/sign-out")
  public ResponseEntity<Void> signOut(@RequestHeader("Authorization") String id) {
    userService.signOut(id);
    return ResponseEntity.ok().build();
  }
}
