package com.mykhailo_pavliuk.smart_cookie.dto;

import com.mykhailo_pavliuk.smart_cookie.model.Subscription;
import com.mykhailo_pavliuk.smart_cookie.model.enums.Role;
import com.mykhailo_pavliuk.smart_cookie.model.enums.Status;
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

  private Status status;

  private Role role;

  private List<Subscription> subscriptions;
}
