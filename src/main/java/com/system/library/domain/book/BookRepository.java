package com.system.library.domain.book;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface BookRepository extends JpaRepository<Book, UUID> {
  Optional<Book> findByTitle(String title);
  Optional<Book> findByTitleAndAuthor(String title, String author);
}
