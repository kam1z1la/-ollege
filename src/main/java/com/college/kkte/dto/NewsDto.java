package com.college.kkte.dto;


import java.time.LocalDate;
import java.time.LocalTime;


public record NewsDto(
      Long id,
      String header,
      String content,
      LocalTime createTimeAt,
      LocalDate createDateAt,
      byte[] photo
) { }
