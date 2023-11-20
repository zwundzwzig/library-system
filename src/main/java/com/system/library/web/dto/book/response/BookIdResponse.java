package com.system.library.web.dto.book.response;

import com.fasterxml.jackson.annotation.JsonProperty;

public record BookIdResponse(@JsonProperty("id") String id) {
}
