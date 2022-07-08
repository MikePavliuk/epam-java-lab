package com.mykhailo_pavliuk.smart_cookie.api;

import com.mykhailo_pavliuk.smart_cookie.dto.PublicationDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

@Api(tags = "Publication management API")
@RequestMapping("/api/v1/publications")
public interface PublicationApi extends CrudApi<PublicationDto, Long> {

  @ApiImplicitParams({
    @ApiImplicitParam(name = "userId", paramType = "path", required = true, value = "User id")
  })
  @ApiOperation("Get all publications which user is subscribed for")
  @ResponseStatus(HttpStatus.OK)
  @GetMapping("/user/{userId}")
  List<PublicationDto> getAllPublicationsByUserId(@PathVariable long userId);
}
