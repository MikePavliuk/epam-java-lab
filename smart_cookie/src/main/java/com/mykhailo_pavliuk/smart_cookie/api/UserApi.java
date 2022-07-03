package com.mykhailo_pavliuk.smart_cookie.api;

import com.mykhailo_pavliuk.smart_cookie.dto.UserDto;
import com.mykhailo_pavliuk.smart_cookie.dto.group.OnCreate;
import com.mykhailo_pavliuk.smart_cookie.dto.group.OnUpdate;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Positive;
import java.math.BigDecimal;
import java.util.List;

@Api(tags = "User management API")
@RequestMapping("/api/v1/users")
public interface UserApi {

	@ApiImplicitParams({
			@ApiImplicitParam(name = "id", paramType = "path", required = true, value = "User id")
	})
	@ApiOperation("Get user")
	@ResponseStatus(HttpStatus.OK)
	@GetMapping("/{id}")
	UserDto getUser(@PathVariable long id);

	@ApiOperation("Get all users")
	@ResponseStatus(HttpStatus.OK)
	@GetMapping()
	List<UserDto> getAllUsers();

	@ApiOperation("Create user")
	@ResponseStatus(HttpStatus.CREATED)
	@PostMapping()
	UserDto createUser(@RequestBody @Validated(OnCreate.class) UserDto userDto);

	@ApiImplicitParams({
			@ApiImplicitParam(name = "id", paramType = "path", required = true, value = "User id")
	})
	@ApiOperation("Update user")
	@ResponseStatus(HttpStatus.OK)
	@PatchMapping("/{id}")
	UserDto updateUser(@PathVariable long id,
					   @RequestBody @Validated(OnUpdate.class) UserDto userDto);

	@ApiImplicitParams({
			@ApiImplicitParam(name = "id", paramType = "path", required = true, value = "User id")
	})
	@ApiOperation("Delete user")
	@ResponseStatus(HttpStatus.OK)
	@DeleteMapping("/{id}")
	ResponseEntity<Void> deleteUser(@PathVariable long id);

	@ApiImplicitParams({
			@ApiImplicitParam(name = "userId", paramType = "path", required = true, value = "User id"),
			@ApiImplicitParam(name = "publicationId", paramType = "path", required = true, value = "Publication id"),
			@ApiImplicitParam(name = "periodInMonths", paramType = "query", required = true, value = "Period of subscription in months")
	})
	@ApiOperation("Subscribe user to publication")
	@ResponseStatus(HttpStatus.OK)
	@PutMapping("/{userId}/publications/{publicationId}")
	UserDto addSubscriptionToUser(@PathVariable long userId,
								  @PathVariable long publicationId,
								  @RequestParam @Positive int periodInMonths);

	@ApiImplicitParams({
			@ApiImplicitParam(name = "id", paramType = "path", required = true, value = "User id"),
			@ApiImplicitParam(name = "amount", paramType = "query", required = true, value = "Amount of money to add")
	})
	@ApiOperation("Add funds to user")
	@ResponseStatus(HttpStatus.OK)
	@PatchMapping("/{id}/add-funds")
	UserDto addFundsToUser(@PathVariable long id,
						   @RequestParam @Positive BigDecimal amount);
}
