package com.mykhailo_pavliuk.smart_cookie.service.impl;

import com.mykhailo_pavliuk.smart_cookie.dto.PublicationDto;
import com.mykhailo_pavliuk.smart_cookie.exception.EntityNotFoundException;
import com.mykhailo_pavliuk.smart_cookie.mapper.PublicationMapper;
import com.mykhailo_pavliuk.smart_cookie.model.Publication;
import com.mykhailo_pavliuk.smart_cookie.model.PublicationInfo;
import com.mykhailo_pavliuk.smart_cookie.repository.GenreRepository;
import com.mykhailo_pavliuk.smart_cookie.repository.LanguageRepository;
import com.mykhailo_pavliuk.smart_cookie.repository.PublicationRepository;
import com.mykhailo_pavliuk.smart_cookie.repository.SubscriptionRepository;
import com.mykhailo_pavliuk.smart_cookie.service.PublicationService;
import java.util.Optional;
import javax.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class PublicationServiceImpl implements PublicationService {

  private final PublicationRepository publicationRepository;
  private final GenreRepository genreRepository;
  private final LanguageRepository languageRepository;
  private final SubscriptionRepository subscriptionRepository;

  @Override
  public PublicationDto getById(Long id) {
    log.info("Started getting publication by id");
    Optional<Publication> publication = publicationRepository.findById(id);
    log.info("Finished getting publication by id ({})", publication);

    return PublicationMapper.INSTANCE.mapPublicationToPublicationDto(
        publication.orElseThrow(EntityNotFoundException::new));
  }

  @Override
  public Page<PublicationDto> getAll(Pageable pageable) {
    log.info("Getting all publications");
    return publicationRepository
        .findAll(pageable)
        .map(PublicationMapper.INSTANCE::mapPublicationToPublicationDto);
  }

  @Transactional
  @Override
  public PublicationDto create(PublicationDto publicationDto) {
    log.info("Started creating publication");

    publicationDto.setGenre(
        genreRepository
            .findByName(publicationDto.getGenre().getName().toLowerCase())
            .orElseThrow(EntityNotFoundException::new));

    publicationDto
        .getPublicationInfos()
        .forEach(
            publicationInfoDto ->
                publicationInfoDto.setLanguage(
                    languageRepository
                        .findByName(publicationInfoDto.getLanguage().getName())
                        .orElseThrow(EntityNotFoundException::new)));

    Publication publication =
        PublicationMapper.INSTANCE.mapPublicationDtoToPublication(publicationDto);

    for (PublicationInfo publicationInfo : publication.getPublicationInfos()) {
      publicationInfo.setPublication(publication);
    }

    publication = publicationRepository.save(publication);

    log.info("Finished creating publication ({})", publication);

    return PublicationMapper.INSTANCE.mapPublicationToPublicationDto(publication);
  }

  @Transactional
  @Override
  public PublicationDto updateById(Long id, PublicationDto publicationDto) {
    log.info("Started updating publication by id");

    if (!publicationRepository.existsById(id)) {
      throw new EntityNotFoundException("Publication with id " + id + " is not found");
    }

    Publication publication =
        PublicationMapper.INSTANCE.mapPublicationDtoToPublication(publicationDto);

    for (PublicationInfo publicationInfo : publication.getPublicationInfos()) {
      publicationInfo.setPublication(publication);
    }

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
}
