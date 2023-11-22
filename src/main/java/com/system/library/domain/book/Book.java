package com.system.library.domain.book;

import com.system.library.domain.auditing.BaseTimeEntity;
import com.system.library.domain.converter.StringToUuidConverter;
import com.system.library.domain.image.Image;
import com.system.library.domain.loan.Loan;
import com.system.library.web.dto.book.request.BookSearchRequest;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.GenericGenerator;

import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "books")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@DynamicUpdate
public class Book extends BaseTimeEntity {
  @Id
  @GeneratedValue(generator = "book")
  @GenericGenerator(name = "book", strategy = "org.hibernate.id.UUIDGenerator")
  @Column(name = "book_id", columnDefinition = "BINARY(16) DEFAULT (UNHEX(REPLACE(UUID(), \"-\", \"\")))")
  @Convert(converter = StringToUuidConverter.class)
  private UUID id;

  @NotBlank(message = "도서 이름은 공백일 수 없어요")
  private String title;

  private String author;

  @OneToMany(mappedBy = "book"
          , fetch = FetchType.LAZY
          , orphanRemoval = true
          , cascade = CascadeType.PERSIST
  )
  @Setter
  private List<Image> images;

  @OneToMany(mappedBy = "book"
          , fetch = FetchType.EAGER
          , orphanRemoval = true
          , cascade = CascadeType.PERSIST
  )
  @Setter
  private List<Loan> loans;

  @Enumerated(EnumType.STRING)
  @Setter
  private BookStatus status;

  public BookSearchRequest toResponse() {
    return BookSearchRequest.builder()
            .id(id.toString())
            .title(title)
            .status(status.getStatus())
            .author(author)
            .images(!images.isEmpty()
                    ? images.stream().map(Image::getUrl).toList()
                    : null)
            .build();
  }
}
