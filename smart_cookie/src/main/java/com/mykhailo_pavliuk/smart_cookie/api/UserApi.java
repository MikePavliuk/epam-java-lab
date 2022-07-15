package com.mykhailo_pavliuk.smart_cookie.api;

import com.mykhailo_pavliuk.smart_cookie.dto.UserDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import java.math.BigDecimal;
import javax.validation.constraints.Email;
import javax.validation.constraints.Positive;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;

@Api(tags = "User management API")
@RequestMapping("/api/v1/users")
public interface UserApi extends CrudApi<UserDto, Long> {

  @ApiImplicitParams({
    @ApiImplicitParam(name = "email", paramType = "query", required = true, value = "User email")
  })
  @ApiOperation("Get (search) user by email")
  @ResponseStatus(HttpStatus.OK)
  @GetMapping("/search")
  UserDto getByEmail(@RequestParam @Email String email);

  @ApiImplicitParams({
    @ApiImplicitParam(name = "userId", paramType = "path", required = true, value = "User id"),
    @ApiImplicitParam(
        name = "publicationId",
        paramType = "path",
        required = true,
        value = "Publication id"),
    @ApiImplicitParam(
        name = "periodInMonths",
        paramType = "query",
        required = true,
        value = "Period of subscription in months")
  })
  @ApiOperation("Subscribe user to publication")
  @ResponseStatus(HttpStatus.OK)
  @PutMapping("/{userId}/publications/{publicationId}")
  UserDto addSubscriptionToUser(
      @PathVariable long userId,
      @PathVariable long publicationId,
      @RequestParam @Positive int periodInMonths);

  @ApiImplicitParams({
    @ApiImplicitParam(name = "id", paramType = "path", required = true, value = "User id"),
    @ApiImplicitParam(
        name = "amount",
        paramType = "query",
        required = true,
        value = "Amount of money to add")
  })
  @ApiOperation("Add funds to user")
  @ResponseStatus(HttpStatus.OK)
  @PatchMapping("/{id}/add-funds")
  UserDto addFundsToUser(@PathVariable long id, @RequestParam @Positive BigDecimal amount);
}
