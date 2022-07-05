package com.mykhailo_pavliuk.smart_cookie.dto;

import java.math.BigDecimal;
import lombok.Data;

@Data
public class UserDetailDto {
  private String firstName;
  private String lastName;
  private BigDecimal balance;
}
