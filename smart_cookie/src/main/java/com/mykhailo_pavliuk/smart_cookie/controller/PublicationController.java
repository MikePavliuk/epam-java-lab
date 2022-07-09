package com.mykhailo_pavliuk.smart_cookie.controller;

import com.mykhailo_pavliuk.smart_cookie.dto.PublicationDto;
import com.mykhailo_pavliuk.smart_cookie.service.PublicationService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/publications")
@RequiredArgsConstructor
public class PublicationController {

  private final PublicationService publicationService;

  @ResponseStatus(HttpStatus.OK)
  @GetMapping("/{id}")
  public PublicationDto getById(@PathVariable long id) {
    log.info("Get publication by id {}", id);
    return publicationService.getById(id);
  }

  @ResponseStatus(HttpStatus.OK)
  @GetMapping
  public List<PublicationDto> getAll() {
    log.info("Get all publications");
    return publicationService.getAll();
  }

  @ResponseStatus(HttpStatus.CREATED)
  @PostMapping
  public PublicationDto create(@RequestBody PublicationDto publicationDto) {
    log.info("Create publication {}", publicationDto);
    return publicationService.create(publicationDto);
  }

  @ResponseStatus(HttpStatus.OK)
  @PatchMapping(value = "/{id}")
  public PublicationDto updateById(
      @PathVariable long id, @RequestBody PublicationDto publicationDto) {
    log.info("Update publication by id {} with request body {}", id, publicationDto);
    return publicationService.updateById(id, publicationDto);
  }

  @ResponseStatus(HttpStatus.OK)
  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deletePublication(@PathVariable long id) {
    log.info("Delete publication by id {}", id);
    publicationService.deleteById(id);
    return ResponseEntity.noContent().build();
  }

  @ResponseStatus(HttpStatus.OK)
  @GetMapping("/user/{userId}")
  public List<PublicationDto> getAllPublicationsByUserId(@PathVariable long userId) {
    log.info("Get all publications of user with id {}", userId);
    return publicationService.getPublicationsByUserId(userId);
  }
}
