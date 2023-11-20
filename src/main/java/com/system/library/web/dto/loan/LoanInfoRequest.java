package com.system.library.web.dto.loan;

import com.system.library.domain.book.Book;
import com.system.library.domain.loan.Loan;
import com.system.library.domain.user.User;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class LoanInfoRequest {
  private String userSeq;
  private String bookSeq;

  public Loan toEntity(Book book, User user) {
    return Loan.builder()
            .user(user)
            .book(book)
            .checkoutDate(LocalDate.now())
            .returnDate(LocalDate.now().plusWeeks(1))
            .build();
  }
}
