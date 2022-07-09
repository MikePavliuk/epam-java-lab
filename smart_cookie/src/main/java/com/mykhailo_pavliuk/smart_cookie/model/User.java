package com.mykhailo_pavliuk.smart_cookie.model;

import lombok.Builder;
import lombok.Data;
import lombok.ToString.Exclude;

@Data
@Builder
public class User {

  private Long id;

  private String email;

  @Exclude private String password;

  private UserDetail userDetail;

  private UserStatus status;

  private Role role;
}
