package com.mykhailo_pavliuk.smart_cookie.dto;

import com.mykhailo_pavliuk.smart_cookie.dto.group.OnCreate;
import lombok.Data;

import javax.validation.constraints.Null;
import javax.validation.constraints.Pattern;
import java.math.BigDecimal;

@Data
public class UserDetailDto {

	@Pattern(regexp = "[A-Z][a-z]{1,49}",
			message = "{validation.user.first_name}")
	private String firstName;

	@Pattern(regexp = "[A-Z][a-z]{1,49}",
			message = "{validation.user.last_name}")
	private String lastName;

	@Null(message = "{validation.balance.null}", groups = OnCreate.class)
	private BigDecimal balance;

}
