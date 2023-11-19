package com.system.library.domain.user;

import com.system.library.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
  Boolean existsByEmail(String email);
  Boolean existsByNickname(String nickname);
}
