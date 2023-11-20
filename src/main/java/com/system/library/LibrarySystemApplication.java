package com.system.library;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class LibrarySystemApplication {

  public static void main(String[] args) {
    SpringApplication.run(LibrarySystemApplication.class, args);
  }

}
