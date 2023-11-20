package com.system.library.service;

import com.system.library.domain.book.Book;
import com.system.library.domain.book.BookRepository;
import com.system.library.domain.book.BookStatus;
import com.system.library.domain.image.Image;
import com.system.library.domain.loan.Loan;
import com.system.library.domain.loan.LoanRepository;
import com.system.library.web.dto.book.request.BookCreateRequest;
import com.system.library.web.dto.book.response.BookIdResponse;
import com.system.library.web.dto.book.request.BookSearchRequest;
import com.system.library.web.dto.loan.LoanInfoRequest;
import com.system.library.web.dto.loan.LoanHistoryInfo;
import com.system.library.web.dto.loan.LoanResultResponse;
import jakarta.persistence.NoResultException;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class BookService {
  private final BookRepository bookRepository;
  private final LoanRepository loanRepository;
  private final ImageService imageService;
  private final UserService userService;

  @Transactional(readOnly = true)
  public Book findById(String id) {
    return bookRepository.findById(UUID.fromString(id))
            .orElseThrow(() -> new NoResultException("해당 ID 값을 가진 도서는 존재하지 않아요."));
  }

  @Transactional(readOnly = true)
  public BookIdResponse findByTitleAndAuthor(String title, String author) {
    Optional<Book> book = Optional.ofNullable(bookRepository.findByTitleAndAuthor(title, author)
            .orElseThrow(() -> new NoResultException("해당 정보를 가진 도서는 존재하지 않아요.")));
    return new BookIdResponse(book.get().getId().toString());
  }

  @Transactional(readOnly = true)
  void checkDuplicatedBookTitle(String title) {
    bookRepository.findByTitle(title).ifPresent(book -> {
      throw new DataIntegrityViolationException("이미 존재하는 도서 이름이에요!");
    });
  }

  @Transactional
  public BookIdResponse create(BookCreateRequest request) {
    checkDuplicatedBookTitle(request.getTitle());

    Book book = request.toEntity();
    Book response = bookRepository.save(book);

    Optional.ofNullable(request.getImages())
            .ifPresent(files -> imageService.saveAll(files, response));

    return new BookIdResponse(response.getId().toString());
  }

  @Transactional
  public BookIdResponse update(BookCreateRequest request, String id) {
    Book targetBook = findById(id);

    Optional.ofNullable(request.getTitle()).ifPresent(targetBook::setTitle);
    Optional.ofNullable(request.getAuthor()).ifPresent(targetBook::setAuthor);
    Optional.ofNullable(request.getImages())
            .ifPresent(files -> imageService.update(files, targetBook));

    return new BookIdResponse(id);
  }

  @Transactional(readOnly = true)
  public BookSearchRequest getBookStatus(String id) {
    Book targetBook = findById(id);
    List<LoanHistoryInfo> historyInfoList = getHistoryInfoListByBook(targetBook);

    BookSearchRequest response = BookSearchRequest.builder()
            .id(targetBook.getId().toString())
            .title(targetBook.getTitle())
            .author(targetBook.getAuthor())
            .status(targetBook.getStatus().getStatus())
            .images(targetBook.getImages().stream().map(Image::getUrl).toList())
            .historyInfoList(historyInfoList)
            .build();

    return response;
  }

  public List<LoanHistoryInfo> getHistoryInfoListByBook(Book book) {
    List<Loan> loanList = loanRepository.findAllByBook(book);
    return loanList.stream().map(Loan::toResponse).toList();
  }

  @Transactional
  public LoanHistoryInfo checkoutBook(LoanInfoRequest request) {
    Book book = findById(request.getBookSeq());

    if (!book.getStatus().equals(BookStatus.AVAILABLE))
      throw new InvalidDataAccessApiUsageException("현재 대출할 수 없는 도서에요");

    Loan loan = request.toEntity(
            book,
            userService.findById(request.getUserSeq())
    );

    book.setStatus(BookStatus.ON_LOAN);
    loan.setStatus(book.getStatus());

    LoanHistoryInfo response = loanRepository.save(loan).toResponse();

    return response;
  }

  @Transactional
  public LoanResultResponse returnBook(LoanInfoRequest request) {
    Book book = findById(request.getBookSeq());
    Loan loan = loanRepository.findByBookAndUser(book, userService.findById(request.getUserSeq())).orElseThrow(() -> new NoResultException("대출 이력이 없어요."));
    if (loan.getStatus().equals(BookStatus.FINISH)) throw new InvalidDataAccessApiUsageException("이미 반납한 도서에요");
    book.setStatus(BookStatus.AVAILABLE);
    loan.setStatus(BookStatus.FINISH);
    return new LoanResultResponse(true);
  }
}
