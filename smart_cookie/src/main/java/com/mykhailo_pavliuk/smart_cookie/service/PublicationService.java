package com.mykhailo_pavliuk.smart_cookie.service;

import com.mykhailo_pavliuk.smart_cookie.dto.PublicationDto;

import java.util.List;

public interface PublicationService {
	PublicationDto getPublication(Long id);

	List<PublicationDto> getAllPublications();

	PublicationDto createPublication(PublicationDto publication);

	PublicationDto updatePublication(Long id, PublicationDto publication);

	void deletePublication(Long id);
}
