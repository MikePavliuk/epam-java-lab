package com.mykhailo_pavliuk.smart_cookie.service.impl;

import com.mykhailo_pavliuk.smart_cookie.dto.PublicationDto;
import com.mykhailo_pavliuk.smart_cookie.mapper.PublicationMapper;
import com.mykhailo_pavliuk.smart_cookie.model.Publication;
import com.mykhailo_pavliuk.smart_cookie.model.Subscription;
import com.mykhailo_pavliuk.smart_cookie.repository.PublicationRepository;
import com.mykhailo_pavliuk.smart_cookie.repository.SubscriptionRepository;
import com.mykhailo_pavliuk.smart_cookie.service.PublicationService;
import java.util.ArrayList;
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
  private final SubscriptionRepository subscriptionRepository;

  @Override
  public PublicationDto getPublication(long id) {
    log.info("Get publication by id {}", id);
    Publication publication = publicationRepository.getPublication(id);
    return PublicationMapper.INSTANCE.mapPublicationToPublicationDto(publication);
  }

  @Override
  public List<PublicationDto> getAllPublications() {
    log.info("Get all publications");
    return publicationRepository.getAllPublications().stream()
        .map(PublicationMapper.INSTANCE::mapPublicationToPublicationDto)
        .collect(Collectors.toList());
  }

  @Override
  public PublicationDto createPublication(PublicationDto publicationDto) {
    log.info("Create publication {}", publicationDto);
    Publication publication =
        PublicationMapper.INSTANCE.mapPublicationDtoToPublication(publicationDto);
    publication = publicationRepository.createPublication(publication);
    return PublicationMapper.INSTANCE.mapPublicationToPublicationDto(publication);
  }

  @Override
  public PublicationDto updatePublication(long id, PublicationDto publicationDto) {
    log.info("Update publication with id {}", id);
    Publication publication =
        PublicationMapper.INSTANCE.mapPublicationDtoToPublication(publicationDto);
    publication = publicationRepository.updatePublication(id, publication);
    return PublicationMapper.INSTANCE.mapPublicationToPublicationDto(publication);
  }

  @Override
  public void deletePublication(long id) {
    log.info("Delete publication with id {}", id);
    publicationRepository.deletePublication(id);
  }

  @Override
  public List<PublicationDto> getPublicationsByUserId(long userId) {
    log.info("Get publications by user id {}", userId);
    List<Subscription> subscriptions = subscriptionRepository.getAllSubscriptionsByUserId(userId);
    List<PublicationDto> publications = new ArrayList<>();
    subscriptions.forEach(
        subscription ->
            publications.add(
                PublicationMapper.INSTANCE.mapPublicationToPublicationDto(
                    publicationRepository.getPublication(subscription.getPublicationId()))));
    return publications;
  }
}
