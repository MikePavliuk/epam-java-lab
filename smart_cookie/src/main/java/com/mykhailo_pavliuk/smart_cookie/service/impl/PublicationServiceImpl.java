package com.mykhailo_pavliuk.smart_cookie.service.impl;

import com.mykhailo_pavliuk.smart_cookie.dto.PublicationDto;
import com.mykhailo_pavliuk.smart_cookie.mapper.PublicationMapper;
import com.mykhailo_pavliuk.smart_cookie.model.Publication;
import com.mykhailo_pavliuk.smart_cookie.repository.PublicationRepository;
import com.mykhailo_pavliuk.smart_cookie.service.PublicationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class PublicationServiceImpl implements PublicationService {

	private final PublicationRepository publicationRepository;

	@Override
	public PublicationDto getPublication(Long id) {
		log.info("Get publication by id {}", id);
		Publication publication = publicationRepository.getPublication(id);
		return PublicationMapper.INSTANCE.mapPublicationToPublicationDto(publication);
	}

	@Override
	public List<PublicationDto> getPaginatedPublications(int page, int size) {
		log.info("Get paginated publications");
		return publicationRepository.getPaginatedPublications(page, size)
				.stream()
				.map(PublicationMapper.INSTANCE::mapPublicationToPublicationDto)
				.collect(Collectors.toList());
	}

	@Override
	public PublicationDto createPublication(PublicationDto publicationDto) {
		log.info("Create publication {}", publicationDto);
		Publication publication = PublicationMapper.INSTANCE.mapPublicationDtoToPublication(publicationDto);
		publication = publicationRepository.createPublication(publication);
		return PublicationMapper.INSTANCE.mapPublicationToPublicationDto(publication);
	}

	@Override
	public PublicationDto updatePublication(Long id, PublicationDto publicationDto) {
		log.info("Update publication with id {}", id);
		Publication publication = PublicationMapper.INSTANCE.mapPublicationDtoToPublication(publicationDto);
		publication = publicationRepository.updatePublication(id, publication);
		return PublicationMapper.INSTANCE.mapPublicationToPublicationDto(publication);
	}

	@Override
	public void deletePublication(Long id) {
		log.info("Delete publication with id {}", id);
		publicationRepository.deletePublication(id);
	}
}
