package com.mykhailo_pavliuk.smart_cookie.dto;

import com.mykhailo_pavliuk.smart_cookie.dto.group.OnCreate;
import com.mykhailo_pavliuk.smart_cookie.dto.group.OnUpdate;
import com.mykhailo_pavliuk.smart_cookie.model.Role;
import com.mykhailo_pavliuk.smart_cookie.model.UserStatus;
import com.mykhailo_pavliuk.smart_cookie.util.validation.unique.Unique;
import java.util.List;
import javax.persistence.Id;
import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import javax.validation.constraints.Pattern;
import lombok.Builder;
import lombok.Data;
import lombok.ToString;

@Data
@Builder
public class UserDto {

  @Id
  @Null(message = "{validation.id.null}", groups = OnCreate.class)
  @NotNull(message = "{validation.id.not_null}", groups = OnUpdate.class)
  private Long id;

  @Email(message = "{validation.user.email}")
  @Unique(message = "{validation.user.emailAlreadyExists}", groups = OnCreate.class)
  private String email;

  @ToString.Exclude
  @Pattern(
      regexp = "(?=^.{8,}$)(?=.*\\d)(?=.*[!@#$%^&*]+)(?![.\\n])(?=.*[A-Z])(?=.*[a-z]).{8,32}$",
      message = "{validation.user.password}")
  private String password;

  @Valid private UserDetailDto userDetail;

  @Null(message = "{validation.status.null}", groups = OnCreate.class)
  @NotNull(message = "{validation.status.not_null}", groups = OnUpdate.class)
  private UserStatus status;

  @Null(message = "{validation.role.null}", groups = OnCreate.class)
  @NotNull(message = "{validation.role.not_null}", groups = OnUpdate.class)
  private Role role;

  @Valid List<SubscriptionDto> subscriptions;
}
