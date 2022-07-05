package com.mykhailo_pavliuk.smart_cookie.model;

import java.time.LocalDate;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Subscription {
  private Long userId;
  private Long publicationId;
  private LocalDate startDate;
  private Integer periodInMonths;
}
