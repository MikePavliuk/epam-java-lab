package com.mykhailo_pavliuk.smart_cookie.controller;

import com.mykhailo_pavliuk.smart_cookie.api.PublicationApi;
import com.mykhailo_pavliuk.smart_cookie.dto.PublicationDto;
import com.mykhailo_pavliuk.smart_cookie.service.PublicationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
public class PublicationController implements PublicationApi {

	private final PublicationService publicationService;

	@Override
	public PublicationDto getPublication(long id) {
		log.info("Get publication by id {}", id);
		return publicationService.getPublication(id);
	}

	@Override
	public List<PublicationDto> getAllPublications() {
		log.info("Get all publications");
		return publicationService.getAllPublications();
	}

	@Override
	public PublicationDto createPublication(PublicationDto publicationDto) {
		log.info("Create publication {}", publicationDto);
		return publicationService.createPublication(publicationDto);
	}

	@Override
	public PublicationDto updatePublication(long id, PublicationDto publicationDto) {
		log.info("Update publication by id {}", id);
		log.trace("Request body publicationDto {}", publicationDto);
		return publicationService.updatePublication(id, publicationDto);
	}

	@Override
	public ResponseEntity<Void> deletePublication(long id) {
		log.info("Delete publication by id {}", id);
		publicationService.deletePublication(id);
		return ResponseEntity.noContent().build();
	}

	@Override
	public List<PublicationDto> getAllPublicationsByUserId(long userId) {
		log.info("Get all publications of user with id {}", userId);
		return publicationService.getPublicationsByUserId(userId);
	}

}
