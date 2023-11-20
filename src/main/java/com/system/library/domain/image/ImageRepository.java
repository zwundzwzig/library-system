package com.system.library.domain.image;

import com.system.library.domain.book.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.Set;
import java.util.UUID;

@Repository
public interface ImageRepository extends JpaRepository<Image, UUID> {
  Collection<Image> findAllByBookIn(Set<Book> bookList);

  @Modifying(clearAutomatically = true, flushAutomatically = true)
  @Query("DELETE FROM Image i WHERE i.book = :book")
  void deleteAllByBook(@Param("book") Book book);
}
