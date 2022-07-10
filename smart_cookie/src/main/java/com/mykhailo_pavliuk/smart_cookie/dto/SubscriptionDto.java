package com.mykhailo_pavliuk.smart_cookie.dto;

import java.time.LocalDate;
import javax.validation.constraints.Positive;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SubscriptionDto {

  private long userId;

  private long publicationId;

  @Positive private int periodInMonths;

  private LocalDate startDate;
}
