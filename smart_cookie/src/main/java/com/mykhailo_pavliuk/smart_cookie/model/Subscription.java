package com.mykhailo_pavliuk.smart_cookie.model;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class Subscription {
    private Long userId;
    private Long publicationId;
    private LocalDate startDate;
    private Integer periodInMonths;
}
