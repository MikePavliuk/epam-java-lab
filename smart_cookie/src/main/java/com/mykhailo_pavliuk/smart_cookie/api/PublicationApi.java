package com.mykhailo_pavliuk.smart_cookie.api;

import com.mykhailo_pavliuk.smart_cookie.dto.PublicationDto;
import com.mykhailo_pavliuk.smart_cookie.dto.group.OnCreate;
import com.mykhailo_pavliuk.smart_cookie.dto.group.OnUpdate;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

@Api(tags = "Publication management API")
@RequestMapping("/api/v1/publications")
public interface PublicationApi {

  @ApiImplicitParams({
    @ApiImplicitParam(name = "id", paramType = "path", required = true, value = "Publication id")
  })
  @ApiOperation("Get publication")
  @ResponseStatus(HttpStatus.OK)
  @GetMapping("/{id}")
  PublicationDto getPublication(@PathVariable long id);

  @ApiOperation("Get all publications")
  @ResponseStatus(HttpStatus.OK)
  @GetMapping
  List<PublicationDto> getAllPublications();

  @ApiOperation("Create publication")
  @ResponseStatus(HttpStatus.CREATED)
  @PostMapping
  PublicationDto createPublication(
      @RequestBody @Validated(OnCreate.class) PublicationDto publicationDto);

  @ApiImplicitParams({
    @ApiImplicitParam(name = "id", paramType = "path", required = true, value = "Publication id"),
  })
  @ApiOperation("Update user")
  @ResponseStatus(HttpStatus.OK)
  @PatchMapping(value = "/{id}")
  PublicationDto updatePublication(
      @PathVariable long id, @RequestBody @Validated(OnUpdate.class) PublicationDto publicationDto);

  @ApiImplicitParams({
    @ApiImplicitParam(name = "id", paramType = "path", required = true, value = "Publication id")
  })
  @ApiOperation("Delete publication")
  @ResponseStatus(HttpStatus.OK)
  @DeleteMapping("/{id}")
  ResponseEntity<Void> deletePublication(@PathVariable long id);

  @ApiImplicitParams({
    @ApiImplicitParam(name = "userId", paramType = "path", required = true, value = "User id")
  })
  @ApiOperation("Get all publications which user is subscribed for")
  @ResponseStatus(HttpStatus.OK)
  @GetMapping("/user/{userId}")
  List<PublicationDto> getAllPublicationsByUserId(@PathVariable long userId);
}
