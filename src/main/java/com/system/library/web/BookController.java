package com.system.library.web;

import com.system.library.service.BookService;
import com.system.library.web.dto.book.request.BookCreateRequest;
import com.system.library.web.dto.book.response.BookIdResponse;
import com.system.library.web.dto.book.request.BookSearchRequest;
import com.system.library.web.dto.loan.LoanInfoRequest;
import com.system.library.web.dto.loan.LoanHistoryInfo;
import com.system.library.web.dto.loan.LoanResultResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/book")
@RequiredArgsConstructor
public class BookController {
  private final BookService bookService;

  @Operation(summary = "도서 등록")
  @ApiResponse(responseCode = "201", description = "Created")
  @PostMapping("")
  public ResponseEntity<BookIdResponse> createBook(@RequestPart(value = "images", required = false) List<MultipartFile> files,
                                                   @RequestPart("request") BookCreateRequest request
  ) {
    request.setImages(files);
    BookIdResponse response = bookService.create(request);

    return ResponseEntity.ok(response);
  }

  @Operation(summary = "도서 대출 이력 확인")
  @ApiResponse(responseCode = "200", description = "Ok")
  @GetMapping("/{id}")
  public ResponseEntity<BookSearchRequest> getBookStatus(@PathVariable String id) {
    return ResponseEntity.ok(bookService.getBookStatus(id));
  }

  @Operation(summary = "전체 도서 목록")
  @ApiResponse(responseCode = "200", description = "Ok")
  @GetMapping("")
  public ResponseEntity<List<BookSearchRequest>> getAllBookList() {
    return ResponseEntity.ok(bookService.getAllBookList());
  }

  @Operation(summary = "도서 수정")
  @ApiResponse(responseCode = "200", description = "Ok")
  @PutMapping("/{id}")
  public ResponseEntity<BookIdResponse> update(
          @PathVariable String id,
          @RequestPart(value = "images", required = false) List<MultipartFile> file,
          @RequestPart("request") BookCreateRequest request
  ) {
    request.setImages(file);
    BookIdResponse response = bookService.update(request, id);
    return ResponseEntity.ok(response);
  }

  @Operation(summary = "도서 대출")
  @ApiResponse(responseCode = "201", description = "Created")
  @PostMapping("/checkout/{bookSeq}")
  public ResponseEntity<LoanHistoryInfo> checkoutBook(HttpServletRequest httpServletRequest, @PathVariable String bookSeq) {
    LoanInfoRequest request = LoanInfoRequest.builder()
            .bookSeq(bookSeq)
            .userSeq(httpServletRequest.getHeader("Authorization"))
            .build();
    LoanHistoryInfo response = bookService.checkoutBook(request);
    return ResponseEntity.ok(response);
  }

  @Operation(summary = "도서 반납")
  @ApiResponse(responseCode = "200", description = "Ok")
  @PutMapping("/return/{bookSeq}")
  public ResponseEntity<LoanResultResponse> returnBook(HttpServletRequest httpServletRequest, @PathVariable String bookSeq) {
    LoanInfoRequest request = LoanInfoRequest.builder()
            .bookSeq(bookSeq)
            .userSeq(httpServletRequest.getHeader("Authorization"))
            .build();
    LoanResultResponse response = bookService.returnBook(request);
    return ResponseEntity.ok(response);
  }
}
