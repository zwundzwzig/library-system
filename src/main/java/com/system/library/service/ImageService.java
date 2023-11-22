package com.system.library.service;

import com.system.library.domain.book.Book;
import com.system.library.domain.image.Image;
import com.system.library.domain.image.ImageRepository;
import com.system.library.global.util.S3Upload;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class ImageService {
  private final S3Upload s3Upload;
  private final ImageRepository imageRepository;
  private final String DIRECTORY_NAME = "book_image";

  @Async
  @Transactional
  public String uploadS3Image(MultipartFile file, String directory) throws IOException {
    return s3Upload.uploadToAws(file, directory);
  }

  @Async
  public CompletableFuture<List<Image>> saveAll(List<MultipartFile> files, Book book) {
    List<Image> images = files.parallelStream()
            .map(file -> setImageEntity(file, book))
            .filter(Objects::nonNull)
            .collect(Collectors.toList());

    List<Image> savedImages = imageRepository.saveAll(images);
    return CompletableFuture.completedFuture(savedImages);
  }

  public void update(List<MultipartFile> files, Book book) {
    deleteAllByBook(book);
    saveAll(files, book);
  }

  private Image setImageEntity(MultipartFile file, Book book) {
    try {
      String url = uploadS3Image(file, DIRECTORY_NAME);
      return Image.builder()
              .url(url)
              .book(book)
              .build();
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  private void deleteAllByBook(Book book) {
    imageRepository.deleteAllByBook(book);
  }

  public List<String> findAllByBookList(Set<Book> bookList) {
    return imageRepository.findAllByBookIn(bookList)
            .stream()
            .map(Image::getUrl)
            .collect(Collectors.toList());
  }
}

