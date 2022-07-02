package com.mykhailo_pavliuk.smart_cookie.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class UserDetailDto {
	private String firstName;
	private String lastName;
	private BigDecimal balance;

}
