package com.mykhailo_pavliuk.smart_cookie.repository.impl;

import com.mykhailo_pavliuk.smart_cookie.model.Publication;
import com.mykhailo_pavliuk.smart_cookie.model.PublicationInfo;
import com.mykhailo_pavliuk.smart_cookie.repository.PublicationRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class PublicationRepositoryImpl implements PublicationRepository {

  private final List<Publication> list = new ArrayList<>();

  @Override
  public Optional<Publication> findById(Long id) {
    log.info("Finding publication by id");
    return list.stream().filter(publication -> publication.getId().equals(id)).findFirst();
  }

  @Override
  public List<Publication> getAll() {
    log.info("Getting all publications");
    return new ArrayList<>(list);
  }

  @Override
  public Publication save(Publication publication) {
    log.info("Saving publication");

    if (publication.getId() != null) {
      list.removeIf(p -> Objects.equals(p.getId(), publication.getId()));
      list.add(0, publication);
      log.info("Finished with updating publication");
    } else {
      publication.setId(list.isEmpty() ? 1L : (list.get(list.size() - 1).getId() + 1));

      long startId =
          list.isEmpty()
              ? 1L
              : (list.get(list.size() - 1)
                      .getPublicationInfos()
                      .get(list.get(list.size() - 1).getPublicationInfos().size() - 1)
                      .getId()
                  + 1);

      for (PublicationInfo publicationInfo : publication.getPublicationInfos()) {
        publicationInfo.setId(startId++);
      }

      list.add(publication);
      log.info("Finished with creating new user");
    }

    return publication;
  }

  @Override
  public void deleteById(Long id) {
    log.info("Deleting publication");
    list.removeIf(publication -> publication.getId().equals(id));
  }

  @Override
  public boolean existsById(Long id) {
    log.info("Check if exists publication by id");
    return list.stream().anyMatch(publication -> publication.getId().equals(id));
  }
}
