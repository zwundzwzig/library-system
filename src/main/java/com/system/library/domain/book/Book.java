package com.system.library.domain.book;

import com.system.library.domain.auditing.BaseTimeEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

@Entity
public class Book extends BaseTimeEntity {
  @Id
  @Column(name = "book_id")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @NotBlank(message = "도서 이름은 공백일 수 없어요")
  private String title;

  private String author;

  private BookStatus status;
}
