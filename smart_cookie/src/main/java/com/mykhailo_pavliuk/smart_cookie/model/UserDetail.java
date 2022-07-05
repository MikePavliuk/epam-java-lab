package com.mykhailo_pavliuk.smart_cookie.model;

import java.math.BigDecimal;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public final class UserDetail {
  private Long id;
  private String firstName;
  private String lastName;
  private BigDecimal balance;
}
