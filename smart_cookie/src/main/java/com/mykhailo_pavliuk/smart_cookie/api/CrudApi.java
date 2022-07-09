package com.mykhailo_pavliuk.smart_cookie.api;

import com.mykhailo_pavliuk.smart_cookie.dto.group.OnCreate;
import com.mykhailo_pavliuk.smart_cookie.dto.group.OnUpdate;
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
import org.springframework.web.bind.annotation.ResponseStatus;

public interface CrudApi<T, I> {
  @ApiImplicitParams({
    @ApiImplicitParam(name = "id", paramType = "path", required = true, value = "Entity id")
  })
  @ApiOperation("Get entity by Id")
  @ResponseStatus(HttpStatus.OK)
  @GetMapping("/{id}")
  T getById(@PathVariable I id);

  @ApiOperation("Get all entities")
  @ResponseStatus(HttpStatus.OK)
  @GetMapping()
  List<T> getAll();

  @ApiOperation("Create entity")
  @ResponseStatus(HttpStatus.CREATED)
  @PostMapping()
  T create(@RequestBody @Validated(OnCreate.class) T entity);

  @ApiImplicitParams({
    @ApiImplicitParam(name = "id", paramType = "path", required = true, value = "Entity id")
  })
  @ApiOperation("Update entity by Id")
  @ResponseStatus(HttpStatus.OK)
  @PatchMapping("/{id}")
  T updateById(@PathVariable I id, @RequestBody @Validated(OnUpdate.class) T entity);

  @ApiImplicitParams({
    @ApiImplicitParam(name = "id", paramType = "path", required = true, value = "Entity id")
  })
  @ApiOperation("Delete entity by Id")
  @ResponseStatus(HttpStatus.OK)
  @DeleteMapping("/{id}")
  ResponseEntity<Void> deleteById(@PathVariable I id);
}
