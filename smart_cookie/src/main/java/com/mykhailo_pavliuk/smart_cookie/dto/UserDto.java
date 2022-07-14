package com.mykhailo_pavliuk.smart_cookie.dto;

import com.mykhailo_pavliuk.smart_cookie.model.Role;
import com.mykhailo_pavliuk.smart_cookie.model.UserStatus;
import java.util.List;
import lombok.Builder;
import lombok.Data;
import lombok.ToString;

@Data
@Builder
public class UserDto {

  private Long id;

  private String email;

  @ToString.Exclude private String password;

  private UserDetailDto userDetail;

  private UserStatus status;

  private Role role;

  List<SubscriptionDto> subscriptions;
}
