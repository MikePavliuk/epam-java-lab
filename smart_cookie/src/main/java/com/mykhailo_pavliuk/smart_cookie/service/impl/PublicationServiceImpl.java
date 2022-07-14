package com.mykhailo_pavliuk.smart_cookie.service.impl;

import com.mykhailo_pavliuk.smart_cookie.dto.PublicationDto;
import com.mykhailo_pavliuk.smart_cookie.exception.EntityNotFoundException;
import com.mykhailo_pavliuk.smart_cookie.mapper.PublicationMapper;
import com.mykhailo_pavliuk.smart_cookie.model.Publication;
import com.mykhailo_pavliuk.smart_cookie.repository.PublicationRepository;
import com.mykhailo_pavliuk.smart_cookie.service.PublicationService;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class PublicationServiceImpl implements PublicationService {

  private final PublicationRepository publicationRepository;

  public PublicationDto getById(Long id) {
    Publication publication =
        publicationRepository
            .findById(id)
            .orElseThrow(
                () -> new EntityNotFoundException(getMessagePublicationIsNotFoundById(id)));
    log.info("Finished getting publication by id ({})", publication);

    return PublicationMapper.INSTANCE.mapPublicationToPublicationDto(publication);
  }

  @Override
  public List<PublicationDto> getAll() {
    log.info("Getting all publications");
    return publicationRepository.getAll().stream()
        .map(PublicationMapper.INSTANCE::mapPublicationToPublicationDto)
        .collect(Collectors.toList());
  }

  @Override
  public PublicationDto create(PublicationDto publicationDto) {
    log.info("Started creating publication");

    Publication publication =
        PublicationMapper.INSTANCE.mapPublicationDtoToPublication(publicationDto);
    publication = publicationRepository.save(publication);

    log.info("Finished creating publication ({})", publication);

    return PublicationMapper.INSTANCE.mapPublicationToPublicationDto(publication);
  }

  @Override
  public PublicationDto updateById(Long id, PublicationDto publicationDto) {
    log.info("Started updating publication by id");

    if (!publicationRepository.existsById(id)) {
      throw new EntityNotFoundException(getMessagePublicationIsNotFoundById(id));
    }

    Publication publication =
        PublicationMapper.INSTANCE.mapPublicationDtoToPublication(publicationDto);

    publication = publicationRepository.save(publication);

    log.info("Finished updating publication by id ({})", publication);

    return PublicationMapper.INSTANCE.mapPublicationToPublicationDto(publication);
  }

  @Override
  public void deleteById(Long id) {
    log.info("Started deleting publication by id");
    publicationRepository.deleteById(id);
    log.info("Finished deleting publication by id");
  }

  private String getMessagePublicationIsNotFoundById(long id) {
    return String.format("Publication with id '%d' is not found!", id);
  }
}
