package com.mykhailo_pavliuk.smart_cookie.dto;

import com.mykhailo_pavliuk.smart_cookie.model.Role;
import com.mykhailo_pavliuk.smart_cookie.model.Status;
import com.mykhailo_pavliuk.smart_cookie.model.Subscription;
import com.mykhailo_pavliuk.smart_cookie.model.UserDetail;
import lombok.Builder;
import lombok.Data;
import lombok.ToString;

import java.util.List;

@Data
@Builder
public class UserDto {

	private Integer id;

	private String email;

	@ToString.Exclude
	private String password;

	private UserDetail userDetail;

	private Status status;

	private Role role;

	private List<Subscription> subscriptions;

}
