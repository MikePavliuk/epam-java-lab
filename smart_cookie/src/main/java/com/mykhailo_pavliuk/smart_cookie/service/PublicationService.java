package com.mykhailo_pavliuk.smart_cookie.service;

import com.mykhailo_pavliuk.smart_cookie.dto.PublicationDto;

import java.util.List;

public interface PublicationService {
	PublicationDto getPublication(long id);

	List<PublicationDto> getAllPublications();

	PublicationDto createPublication(PublicationDto publicationDto);

	PublicationDto updatePublication(long id, PublicationDto publicationDto);

	void deletePublication(long id);

	List<PublicationDto> getPublicationsByUserId(long userId);
}
