package com.system.library.domain.loan;

import com.system.library.domain.auditing.BaseTimeEntity;
import com.system.library.domain.book.Book;
import com.system.library.domain.user.User;
import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
public class Loan extends BaseTimeEntity {
  @Id
  @Column(name = "loan_id")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne
  private User user;

  @ManyToOne
  private Book book;

  private LocalDate returnDate;
}

