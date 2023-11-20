package com.system.library.domain.converter;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import java.util.UUID;

@Converter
public class StringToUuidConverter implements AttributeConverter<UUID, String> {
  @Override
  public String convertToDatabaseColumn(UUID uuid) {
    return uuid != null ? uuid.toString().replace("-", "") : null;
  }

  @Override
  public UUID convertToEntityAttribute(String uuid) {
    return uuid != null ? UUID.fromString(uuid) : null;
  }
}
