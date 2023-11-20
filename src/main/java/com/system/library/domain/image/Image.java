package com.system.library.domain.image;

import com.system.library.domain.book.Book;
import com.system.library.domain.converter.StringToUuidConverter;
import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import java.util.UUID;

@Entity
@Table(name = "images")
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Image {
  @Id
  @GeneratedValue(generator = "image")
  @GenericGenerator(name = "image", strategy = "org.hibernate.id.UUIDGenerator")
  @Column(name = "image_id", columnDefinition = "BINARY(16) DEFAULT (UNHEX(REPLACE(UUID(), \"-\", \"\")))")
  @Convert(converter = StringToUuidConverter.class)
  private UUID id;

  @Nullable
  private String url;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "book_id")
  @NotNull
  private Book book;
}
