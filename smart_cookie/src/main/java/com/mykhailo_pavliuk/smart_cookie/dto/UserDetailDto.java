package com.mykhailo_pavliuk.smart_cookie.dto;

import java.math.BigDecimal;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserDetailDto {

  private Long id;

  private String firstName;

  private String lastName;

  private BigDecimal balance;
}
