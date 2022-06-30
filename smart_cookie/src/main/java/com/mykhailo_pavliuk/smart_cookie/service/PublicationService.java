package com.mykhailo_pavliuk.smart_cookie.service;

import com.mykhailo_pavliuk.smart_cookie.dto.PublicationDto;

import java.util.List;

public interface PublicationService {
	PublicationDto getPublication(Long id);

	List<PublicationDto> getAllPublications();

	List<PublicationDto> getPaginatedPublications(int page, int size);

	PublicationDto createPublication(PublicationDto publicationDto);

	PublicationDto updatePublication(Long id, PublicationDto publicationDto);

	void deletePublication(Long id);
}
