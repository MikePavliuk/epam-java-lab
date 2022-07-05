package com.mykhailo_pavliuk.smart_cookie.repository.impl;

import com.mykhailo_pavliuk.smart_cookie.exception.EntityNotFoundException;
import com.mykhailo_pavliuk.smart_cookie.model.Publication;
import com.mykhailo_pavliuk.smart_cookie.repository.PublicationRepository;
import java.util.ArrayList;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class PublicationRepositoryImpl implements PublicationRepository {

  private final List<Publication> list = new ArrayList<>();

  @Override
  public Publication getPublication(long id) {
    log.info("Get publication with id {}", id);
    return list.stream()
        .filter(publication -> publication.getId() == id)
        .findFirst()
        .orElseThrow(() -> new EntityNotFoundException("Publication is not found"));
  }

  @Override
  public List<Publication> getAllPublications() {
    log.info("Get all publications");
    return new ArrayList<>(list);
  }

  @Override
  public Publication createPublication(Publication publication) {
    log.info("Create publication {}", publication);
    publication.setId((long) (list.size() + 1));
    list.add(publication);
    return publication;
  }

  @Override
  public Publication updatePublication(long id, Publication publication) {
    log.info("Update publication with id {}", id);
    boolean isDeleted = list.removeIf(p -> p.getId().equals(id));
    if (isDeleted) {
      list.add(publication);
    } else {
      throw new EntityNotFoundException("Publication is not found");
    }
    return publication;
  }

  @Override
  public void deletePublication(long id) {
    log.info("Delete publication with id {}", id);
    list.removeIf(publication -> publication.getId().equals(id));
  }
}
