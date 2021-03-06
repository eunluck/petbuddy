package com.petbuddy.api.util;

import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DateTimeUtils {

  public static Timestamp timestampOf(LocalDateTime time) {
    return time == null ? null : Timestamp.valueOf(time);
  }

  public static LocalDateTime dateTimeOf(Timestamp timestamp) {
    return timestamp == null ? null : timestamp.toLocalDateTime();
  }

  public static LocalDate minAgeYearCalculation(Integer age){
    if (age == null){
      return null;
    }
    LocalDate now = LocalDate.now();

    return LocalDate.of(now.minusYears(age).getYear(),1,1);
  }

  public static LocalDate maxAgeYearCalculation(Integer age){
    if (age == null){
      return null;
    }

    LocalDate now = LocalDate.now();


    return LocalDate.of(now.minusYears(age).getYear(),1,1);
  }



}
