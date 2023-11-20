package com.system.library.web.dto.loan;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LoanHistoryInfo {
  private String userId;
  private String status;
  private String checkoutDate;
  private String returnDate;
}
