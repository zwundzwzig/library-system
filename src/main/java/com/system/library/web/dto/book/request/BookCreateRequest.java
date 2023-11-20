package com.system.library.web.dto.book.request;

import com.system.library.domain.book.Book;
import com.system.library.domain.book.BookStatus;
import lombok.Builder;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Data
@Builder
public class BookCreateRequest {
  private List<MultipartFile> images;
  private String title;
  private String author;

  public Book toEntity() {
    return Book.builder()
            .title(title)
            .author(author)
            .status(BookStatus.AVAILABLE)
            .build();
  }
}
