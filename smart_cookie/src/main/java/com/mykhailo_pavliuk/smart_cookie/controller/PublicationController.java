package com.mykhailo_pavliuk.smart_cookie.controller;

import com.mykhailo_pavliuk.smart_cookie.dto.PublicationDto;
import com.mykhailo_pavliuk.smart_cookie.service.PublicationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
	public PublicationDto getPublication(@PathVariable Long id) {
		log.info("Get publication by id {}", id);
		return publicationService.getPublication(id);
	}

	@ResponseStatus(HttpStatus.OK)
	@GetMapping
	public List<PublicationDto> getAllPublication() {
		log.info("Get all publications");
		return publicationService.getAllPublications();
	}

	@ResponseStatus(HttpStatus.CREATED)
	@PostMapping
	public PublicationDto createPublication(@RequestBody PublicationDto publicationDto) {
		log.info("Create publication {}", publicationDto);
		return publicationService.createPublication(publicationDto);
	}

	@ResponseStatus(HttpStatus.OK)
	@PutMapping(value = "/{id}")
	public PublicationDto updateUser(@PathVariable Long id, @RequestBody PublicationDto publicationDto) {
		log.info("Update publication by id {}", id);
		log.trace("Request body publicationDto {}", publicationDto);
		return publicationService.updatePublication(id, publicationDto);
	}

	@ResponseStatus(HttpStatus.OK)
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
		log.info("Delete publication by id {}", id);
		publicationService.deletePublication(id);
		return ResponseEntity.noContent().build();
	}

}
