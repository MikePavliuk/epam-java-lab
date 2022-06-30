package com.mykhailo_pavliuk.smart_cookie.model;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public final class UserDetail {
	private Long id;
    private String firstName;
    private String lastName;
    private BigDecimal balance;
}
