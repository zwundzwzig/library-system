package com.system.library.web.dto.user.response;

import com.fasterxml.jackson.annotation.JsonProperty;

public record UserNicknameResponse(@JsonProperty("nickname") String nickname) {
}
