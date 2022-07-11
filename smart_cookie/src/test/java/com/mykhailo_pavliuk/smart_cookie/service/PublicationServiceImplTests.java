package com.mykhailo_pavliuk.smart_cookie.service;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatExceptionOfType;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasItems;
import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.mykhailo_pavliuk.smart_cookie.dto.PublicationDto;
import com.mykhailo_pavliuk.smart_cookie.exception.EntityNotFoundException;
import com.mykhailo_pavliuk.smart_cookie.mapper.PublicationMapper;
import com.mykhailo_pavliuk.smart_cookie.model.Genre;
import com.mykhailo_pavliuk.smart_cookie.model.Language;
import com.mykhailo_pavliuk.smart_cookie.model.Publication;
import com.mykhailo_pavliuk.smart_cookie.repository.GenreRepository;
import com.mykhailo_pavliuk.smart_cookie.repository.LanguageRepository;
import com.mykhailo_pavliuk.smart_cookie.repository.PublicationRepository;
import com.mykhailo_pavliuk.smart_cookie.service.impl.PublicationServiceImpl;
import com.mykhailo_pavliuk.smart_cookie.test.util.PublicationTestDataUtil;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

@ExtendWith(MockitoExtension.class)
class PublicationServiceImplTests {

  @InjectMocks private PublicationServiceImpl publicationService;

  @Mock private PublicationRepository publicationRepository;
  @Mock private GenreRepository genreRepository;
  @Mock private LanguageRepository languageRepository;

  @Test
  void givenExistingPublicationId_whenGetById_thenReturnPublication() {
    Publication publication = PublicationTestDataUtil.createPublication();

    when(publicationRepository.findById(publication.getId())).thenReturn(Optional.of(publication));

    PublicationDto publicationDto = publicationService.getById(publication.getId());

    assertThat(
        publicationDto,
        allOf(
            hasProperty("id", equalTo(publicationDto.getId())),
            hasProperty("publicationInfos", hasSize(1)),
            hasProperty("pricePerMonth", equalTo(publicationDto.getPricePerMonth())),
            hasProperty("genre", equalTo(publicationDto.getGenre()))));

    verify(publicationRepository, times(1)).findById(publication.getId());
  }

  @Test
  void givenNotExistingPublicationId_whenGetById_thenThrowEntityNotFoundException() {
    when(publicationRepository.findById(PublicationTestDataUtil.ID)).thenReturn(Optional.empty());

    assertThatExceptionOfType(EntityNotFoundException.class)
        .isThrownBy(() -> publicationService.getById(PublicationTestDataUtil.ID))
        .withMessage("Entity is not found");

    verify(publicationRepository, times(1)).findById(PublicationTestDataUtil.ID);
  }

  @Test
  void givenEmptyPageable_whenGetAll_thenReturnListOfAllPublications() {
    PublicationDto publicationDto1 = PublicationTestDataUtil.createPublicationDto();

    PublicationDto publicationDto2 = PublicationTestDataUtil.createPublicationDto();
    publicationDto2.setId(2L);

    PublicationDto publicationDto3 = PublicationTestDataUtil.createPublicationDto();
    publicationDto2.setId(3L);

    Publication publication1 =
        PublicationMapper.INSTANCE.mapPublicationDtoToPublication(publicationDto1);
    Publication publication2 =
        PublicationMapper.INSTANCE.mapPublicationDtoToPublication(publicationDto2);
    Publication publication3 =
        PublicationMapper.INSTANCE.mapPublicationDtoToPublication(publicationDto3);

    List<Publication> publications = Arrays.asList(publication1, publication2, publication3);

    Pageable pageable = Pageable.unpaged();

    when(publicationRepository.findAll(pageable))
        .thenReturn(new PageImpl<>(publications, pageable, publications.size()));

    Page<PublicationDto> page = publicationService.getAll(pageable);

    assertThat(page.getContent(), hasItems(publicationDto1, publicationDto2, publicationDto3));

    verify(publicationRepository, times(1)).findAll(pageable);
  }

  @Test
  void givenPageable_whenGetAll_thenReturnPageableUsers() {
    PublicationDto publicationDto1 = PublicationTestDataUtil.createPublicationDto();

    PublicationDto publicationDto2 = PublicationTestDataUtil.createPublicationDto();
    publicationDto2.setId(2L);

    PublicationDto publicationDto3 = PublicationTestDataUtil.createPublicationDto();
    publicationDto2.setId(3L);

    Publication publication1 =
        PublicationMapper.INSTANCE.mapPublicationDtoToPublication(publicationDto1);
    Publication publication2 =
        PublicationMapper.INSTANCE.mapPublicationDtoToPublication(publicationDto2);
    Publication publication3 =
        PublicationMapper.INSTANCE.mapPublicationDtoToPublication(publicationDto3);

    List<Publication> publications = Arrays.asList(publication1, publication2, publication3);

    Pageable pageable = PageRequest.of(0, 2);

    when(publicationRepository.findAll(pageable))
        .thenReturn(new PageImpl<>(publications, pageable, publications.size()));

    Page<PublicationDto> page = publicationService.getAll(pageable);

    assertThat(page.getSize(), is(2));
    assertThat(page.getPageable(), is(pageable));
    assertThat(page.getContent(), hasItems(publicationDto1, publicationDto2));

    verify(publicationRepository, times(1)).findAll(pageable);
  }

  @Test
  void givenValidPublicationDto_whenCreate_thenReturnCreatedPublicationDto() {
    PublicationDto inputPublicationDto = PublicationTestDataUtil.createPublicationDto();
    Genre genre = PublicationTestDataUtil.GENRE;
    Language language = PublicationTestDataUtil.PUBLICATION_INFO_LANGUAGE;
    Publication expectedConfiguredPublication = PublicationTestDataUtil.createPublication();

    when(genreRepository.findByName(genre.getName())).thenReturn(Optional.of(genre));
    when(languageRepository.findByName(language.getName())).thenReturn(Optional.of(language));
    when(publicationRepository.save(expectedConfiguredPublication))
        .thenReturn(expectedConfiguredPublication);

    PublicationDto createdPublication = publicationService.create(inputPublicationDto);

    assertThat(
        createdPublication,
        allOf(
            hasProperty("id", equalTo(expectedConfiguredPublication.getId())),
            hasProperty("pricePerMonth", equalTo(expectedConfiguredPublication.getPricePerMonth())),
            hasProperty("genre", equalTo(expectedConfiguredPublication.getGenre()))));

    verify(genreRepository, times(1)).findByName(genre.getName());
    verify(languageRepository, times(1)).findByName(language.getName());
    verify(publicationRepository, times(1)).save(expectedConfiguredPublication);
  }

  @Test
  void givenPublicationDtoWithInvalidGenre_whenCreate_thenThrowEntityNotFoundException() {
    PublicationDto inputPublicationDto = PublicationTestDataUtil.createPublicationDto();
    Genre genre = PublicationTestDataUtil.GENRE;
    genre.setId(1000L);
    Language language = PublicationTestDataUtil.PUBLICATION_INFO_LANGUAGE;
    Publication expectedConfiguredPublication = PublicationTestDataUtil.createPublication();

    when(genreRepository.findByName(genre.getName())).thenReturn(Optional.empty());

    assertThatExceptionOfType(EntityNotFoundException.class)
        .isThrownBy(() -> publicationService.create(inputPublicationDto))
        .withMessage("Entity is not found");

    verify(genreRepository, times(1)).findByName(genre.getName());
    verify(languageRepository, times(0)).findByName(language.getName());
    verify(publicationRepository, times(0)).save(expectedConfiguredPublication);
  }

  @Test
  void givenPublicationDtoWithInvalidLanguage_whenCreate_thenThrowEntityNotFoundException() {
    PublicationDto inputPublicationDto = PublicationTestDataUtil.createPublicationDto();
    Genre genre = PublicationTestDataUtil.GENRE;
    Language language = PublicationTestDataUtil.PUBLICATION_INFO_LANGUAGE;
    language.setId(1000L);
    Publication expectedConfiguredPublication = PublicationTestDataUtil.createPublication();

    when(genreRepository.findByName(genre.getName())).thenReturn(Optional.of(genre));
    when(languageRepository.findByName(language.getName())).thenReturn(Optional.empty());

    assertThatExceptionOfType(EntityNotFoundException.class)
        .isThrownBy(() -> publicationService.create(inputPublicationDto))
        .withMessage("Entity is not found");

    verify(genreRepository, times(1)).findByName(genre.getName());
    verify(languageRepository, times(1)).findByName(language.getName());
    verify(publicationRepository, times(0)).save(expectedConfiguredPublication);
  }

  @Test
  void givenExistingPublicationId_whenUpdateById_thenReturnUpdatedPublication() {
    PublicationDto publicationDto = PublicationTestDataUtil.createPublicationDto();
    Publication publication =
        PublicationMapper.INSTANCE.mapPublicationDtoToPublication(publicationDto);

    when(publicationRepository.existsById(publicationDto.getId())).thenReturn(true);
    when(publicationRepository.save(publication)).thenReturn(publication);

    PublicationDto updatedPublication =
        publicationService.updateById(publicationDto.getId(), publicationDto);

    assertThat(updatedPublication, is(publicationDto));

    verify(publicationRepository, times(1)).existsById(publicationDto.getId());
    verify(publicationRepository, times(1)).save(publication);
  }

  @Test
  void givenNotExistingPublicationId_whenUpdateById_thenThrowEntityNotFoundException() {
    PublicationDto publicationDto = PublicationTestDataUtil.createPublicationDto();

    when(publicationRepository.existsById(publicationDto.getId())).thenReturn(false);

    assertThatExceptionOfType(EntityNotFoundException.class)
        .isThrownBy(() -> publicationService.updateById(publicationDto.getId(), publicationDto))
        .withMessage("Publication with id " + publicationDto.getId() + " is not found");

    verify(publicationRepository, times(1)).existsById(publicationDto.getId());
    verify(publicationRepository, times(0)).save(any());
  }

  @Test
  void givenPublicationId_whenDeleteById_thenCallRepositoryMethod() {
    doNothing().when(publicationRepository).deleteById(anyLong());

    publicationService.deleteById(1L);

    verify(publicationRepository, times(1)).deleteById(1L);
  }
}
