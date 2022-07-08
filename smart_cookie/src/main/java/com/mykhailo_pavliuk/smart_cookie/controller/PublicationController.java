package com.mykhailo_pavliuk.smart_cookie.controller;

import com.mykhailo_pavliuk.smart_cookie.api.PublicationApi;
import com.mykhailo_pavliuk.smart_cookie.dto.PublicationDto;
import com.mykhailo_pavliuk.smart_cookie.service.PublicationService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
public class PublicationController implements PublicationApi {

  private final PublicationService publicationService;

  @Override
  public PublicationDto getPublication(long id) {
    log.info("Get publication by id {}", id);
    return publicationService.getById(id);
  }

  @Override
  public Page<PublicationDto> getAllPublications(Pageable pageable) {
    log.info("Get all publications");
    return publicationService.getAll(pageable);
  }

  @Override
  public PublicationDto createPublication(PublicationDto publicationDto) {
    log.info("Create publication {}", publicationDto);
    return publicationService.create(publicationDto);
  }

  @Override
  public PublicationDto updatePublication(long id, PublicationDto publicationDto) {
    log.info("Update publication by id {}", id);
    log.trace("Request body publicationDto {}", publicationDto);
    return publicationService.updateById(id, publicationDto);
  }

  @Override
  public ResponseEntity<Void> deletePublication(long id) {
    log.info("Delete publication by id {}", id);
    publicationService.deleteById(id);
    return ResponseEntity.noContent().build();
  }

  @Override
  public List<PublicationDto> getAllPublicationsByUserId(long userId) {
    log.info("Get all publications of user with id {}", userId);
    return publicationService.getPublicationsByUserId(userId);
  }
}
