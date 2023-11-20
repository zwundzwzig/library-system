package com.system.library.web.dto.book.request;

import com.system.library.web.dto.loan.LoanHistoryInfo;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class BookSearchRequest {
  private String id;
  private String title;
  private String author;
  private String status;
  private List<String> images;
  private List<LoanHistoryInfo> historyInfoList;
}
