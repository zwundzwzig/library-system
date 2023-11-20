package com.system.library.domain.book;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum BookStatus {
  AVAILABLE("대출 가능"),
  ON_LOAN("대출 중"),
  FINISH("반납"),
  PAST_DUE("연체 중");

  private final String status;
}
