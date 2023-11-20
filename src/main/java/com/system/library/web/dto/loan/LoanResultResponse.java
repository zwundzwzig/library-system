package com.system.library.web.dto.loan;

import com.fasterxml.jackson.annotation.JsonProperty;

public record LoanResultResponse(@JsonProperty("isReturn") Boolean isReturn) {
}
