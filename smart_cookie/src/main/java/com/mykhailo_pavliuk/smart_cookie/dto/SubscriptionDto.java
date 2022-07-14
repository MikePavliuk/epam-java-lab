package com.mykhailo_pavliuk.smart_cookie.dto;

import java.time.LocalDate;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SubscriptionDto {

  private long userId;

  private long publicationId;

  private int periodInMonths;

  private LocalDate startDate;
}
