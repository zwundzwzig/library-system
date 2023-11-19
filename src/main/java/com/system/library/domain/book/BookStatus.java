package com.system.library.domain.book;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum BookStatus {
  AVAILABLE("대출 가능"),
  ON_LOAN("대출 중"),
  RESERVED("예약 중"),
  DAMAGED("손상됨"),
  LOST("분실됨"),
  UNDER_REPAIR("수리 중");

  private final String displayValue;
}
