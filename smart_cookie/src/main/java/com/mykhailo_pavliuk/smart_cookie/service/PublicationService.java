package com.mykhailo_pavliuk.smart_cookie.service;

import com.mykhailo_pavliuk.smart_cookie.dto.PublicationDto;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PublicationService {
  PublicationDto getPublication(long id);

  Page<PublicationDto> getAllPublications(Pageable pageable);

  PublicationDto createPublication(PublicationDto publicationDto);

  PublicationDto updatePublication(long id, PublicationDto publicationDto);

  void deletePublication(long id);

  List<PublicationDto> getPublicationsByUserId(long userId);
}
