package com.system.library.web.dto.book.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.system.library.web.dto.loan.LoanHistoryInfo;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BookSearchRequest {
  private String id;
  private String title;
  private String author;
  private String status;
  private List<String> images;
  private List<LoanHistoryInfo> historyInfoList;
}
