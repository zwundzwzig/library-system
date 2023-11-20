package com.system.library.domain.loan;

import com.system.library.domain.auditing.BaseTimeEntity;
import com.system.library.domain.book.Book;
import com.system.library.domain.book.BookStatus;
import com.system.library.domain.converter.StringToUuidConverter;
import com.system.library.domain.user.User;
import com.system.library.web.dto.loan.LoanHistoryInfo;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.GenericGenerator;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "loans")
@Getter
@NoArgsConstructor
@SuperBuilder
@AllArgsConstructor
@Builder
@DynamicUpdate
public class Loan extends BaseTimeEntity {
  @Id
  @GeneratedValue(generator = "loan")
  @GenericGenerator(name = "loan", strategy = "org.hibernate.id.UUIDGenerator")
  @Column(name = "loan_id", columnDefinition = "BINARY(16) DEFAULT (UNHEX(REPLACE(UUID(), \"-\", \"\")))")
  @Convert(converter = StringToUuidConverter.class)
  private UUID id;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "user_id")
  private User user;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "book_id")
  private Book book;

  private LocalDate checkoutDate;
  private LocalDate returnDate;

  @Enumerated(EnumType.STRING)
  @Setter
  private BookStatus status;

  public LoanHistoryInfo toResponse() {
    return LoanHistoryInfo.builder()
            .userId(user.getId().toString())
            .status(status.getStatus())
            .checkoutDate(checkoutDate.toString())
            .returnDate(returnDate.toString())
            .build();
  }
}

