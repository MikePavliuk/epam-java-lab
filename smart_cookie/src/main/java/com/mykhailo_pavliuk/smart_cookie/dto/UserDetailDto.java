package com.mykhailo_pavliuk.smart_cookie.dto;

import lombok.Data;

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

	private BigDecimal balance;

}
