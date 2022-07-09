package com.mykhailo_pavliuk.smart_cookie.dto;

import com.mykhailo_pavliuk.smart_cookie.dto.group.OnCreate;
import com.mykhailo_pavliuk.smart_cookie.dto.group.OnUpdate;
import java.math.BigDecimal;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import javax.validation.constraints.Pattern;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserDetailDto {

  @Null(message = "{validation.id.null}", groups = OnCreate.class)
  @NotNull(message = "{validation.id.not_null}", groups = OnUpdate.class)
  private Long id;

  @Pattern(regexp = "[A-Z][a-z]{1,49}", message = "{validation.user.first_name}")
  private String firstName;

  @Pattern(regexp = "[A-Z][a-z]{1,49}", message = "{validation.user.last_name}")
  private String lastName;

  @Null(message = "{validation.balance.null}", groups = OnCreate.class)
  private BigDecimal balance;
}
