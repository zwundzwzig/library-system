package com.system.library.domain.loan;

import com.system.library.domain.book.Book;
import com.system.library.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface LoanRepository extends JpaRepository<Loan, UUID> {
  List<Loan> findAllByBook(Book book);
  Optional<Loan> findByBookAndUser(Book book, User user);
}
