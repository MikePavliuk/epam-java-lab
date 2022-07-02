package com.mykhailo_pavliuk.smart_cookie.controller;

import com.mykhailo_pavliuk.smart_cookie.dto.PublicationDto;
import com.mykhailo_pavliuk.smart_cookie.dto.group.OnCreate;
import com.mykhailo_pavliuk.smart_cookie.dto.group.OnUpdate;
import com.mykhailo_pavliuk.smart_cookie.service.PublicationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/publications")
@RequiredArgsConstructor
public class PublicationController {

	private final PublicationService publicationService;

	@ResponseStatus(HttpStatus.OK)
	@GetMapping("/{id}")
	public PublicationDto getPublication(@PathVariable long id) {
		log.info("Get publication by id {}", id);
		return publicationService.getPublication(id);
	}

	@ResponseStatus(HttpStatus.OK)
	@GetMapping
	public List<PublicationDto> getAllPublications() {
		log.info("Get all publications");
		return publicationService.getAllPublications();
	}

	@ResponseStatus(HttpStatus.CREATED)
	@PostMapping
	public PublicationDto createPublication(@Validated(OnCreate.class) @RequestBody PublicationDto publicationDto) {
		log.info("Create publication {}", publicationDto);
		return publicationService.createPublication(publicationDto);
	}

	@ResponseStatus(HttpStatus.OK)
	@PutMapping(value = "/{id}")
	public PublicationDto updatePublication(@PathVariable long id, @Validated(OnUpdate.class) @RequestBody PublicationDto publicationDto) {
		log.info("Update publication by id {}", id);
		log.trace("Request body publicationDto {}", publicationDto);
		return publicationService.updatePublication(id, publicationDto);
	}

	@ResponseStatus(HttpStatus.OK)
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deletePublication(@PathVariable long id) {
		log.info("Delete publication by id {}", id);
		publicationService.deletePublication(id);
		return ResponseEntity.noContent().build();
	}

	@ResponseStatus(HttpStatus.OK)
	@GetMapping("/user/{userId}")
	public List<PublicationDto> getAllPublicationsByUserId(@PathVariable long userId) {
		log.info("Get all publications of user with id {}", userId);
		return publicationService.getPublicationsByUserId(userId);
	}

}
