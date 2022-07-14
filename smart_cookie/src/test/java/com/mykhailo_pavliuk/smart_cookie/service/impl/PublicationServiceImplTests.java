package com.mykhailo_pavliuk.smart_cookie.service.impl;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatExceptionOfType;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasItems;
import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.mykhailo_pavliuk.smart_cookie.dto.PublicationDto;
import com.mykhailo_pavliuk.smart_cookie.exception.EntityNotFoundException;
import com.mykhailo_pavliuk.smart_cookie.mapper.PublicationMapper;
import com.mykhailo_pavliuk.smart_cookie.model.Publication;
import com.mykhailo_pavliuk.smart_cookie.repository.GenreRepository;
import com.mykhailo_pavliuk.smart_cookie.repository.LanguageRepository;
import com.mykhailo_pavliuk.smart_cookie.repository.PublicationRepository;
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
  void givenExistingPublicationId_whenGetById_thenReturnGotPublicationDto() {
    Publication publication = PublicationTestDataUtil.createPublication();

    when(publicationRepository.findById(PublicationTestDataUtil.ID))
        .thenReturn(Optional.of(publication));

    PublicationDto gotPublicationDto = publicationService.getById(PublicationTestDataUtil.ID);

    assertThat(
        gotPublicationDto,
        allOf(
            hasProperty("id", equalTo(PublicationTestDataUtil.ID)),
            hasProperty("publicationInfos", hasSize(1)),
            hasProperty("pricePerMonth", equalTo(PublicationTestDataUtil.PRICE_PER_MONTH)),
            hasProperty("genre", equalTo(PublicationTestDataUtil.GENRE))));

    verify(publicationRepository, times(1)).findById(PublicationTestDataUtil.ID);
  }

  @Test
  void givenNotExistingPublicationId_whenGetById_thenThrowEntityNotFoundException() {
    when(publicationRepository.findById(PublicationTestDataUtil.ID)).thenReturn(Optional.empty());

    assertThatExceptionOfType(EntityNotFoundException.class)
        .isThrownBy(() -> publicationService.getById(PublicationTestDataUtil.ID))
        .withMessage("Publication with id '" + PublicationTestDataUtil.ID + "' is not found!");

    verify(publicationRepository, times(1)).findById(PublicationTestDataUtil.ID);
  }

  @Test
  void givenEmptyPageable_whenGetAll_thenReturnListOfAllPublications() {
    PublicationDto publicationDto1 = PublicationTestDataUtil.createPublicationDto();

    PublicationDto publicationDto2 = PublicationTestDataUtil.createPublicationDto();
    publicationDto2.setId(2L);

    PublicationDto publicationDto3 = PublicationTestDataUtil.createPublicationDto();
    publicationDto2.setId(3L);

    List<Publication> publications =
        Arrays.asList(
            PublicationMapper.INSTANCE.mapPublicationDtoToPublication(publicationDto1),
            PublicationMapper.INSTANCE.mapPublicationDtoToPublication(publicationDto2),
            PublicationMapper.INSTANCE.mapPublicationDtoToPublication(publicationDto3));

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

    List<Publication> publications =
        Arrays.asList(
            PublicationMapper.INSTANCE.mapPublicationDtoToPublication(publicationDto1),
            PublicationMapper.INSTANCE.mapPublicationDtoToPublication(publicationDto2),
            PublicationMapper.INSTANCE.mapPublicationDtoToPublication(publicationDto3));

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
    Publication expectedConfiguredPublication = PublicationTestDataUtil.createPublication();

    when(genreRepository.findByName(PublicationTestDataUtil.GENRE.getName()))
        .thenReturn(Optional.of(PublicationTestDataUtil.GENRE));
    when(languageRepository.findByName(PublicationTestDataUtil.PUBLICATION_INFO_LANGUAGE.getName()))
        .thenReturn(Optional.of(PublicationTestDataUtil.PUBLICATION_INFO_LANGUAGE));
    when(publicationRepository.save(expectedConfiguredPublication))
        .thenReturn(expectedConfiguredPublication);

    PublicationDto createdPublication = publicationService.create(inputPublicationDto);

    assertThat(
        createdPublication,
        allOf(
            hasProperty("id", equalTo(expectedConfiguredPublication.getId())),
            hasProperty("pricePerMonth", equalTo(expectedConfiguredPublication.getPricePerMonth())),
            hasProperty("genre", equalTo(expectedConfiguredPublication.getGenre()))));

    verify(genreRepository, times(1)).findByName(PublicationTestDataUtil.GENRE.getName());
    verify(languageRepository, times(1))
        .findByName(PublicationTestDataUtil.PUBLICATION_INFO_LANGUAGE.getName());
    verify(publicationRepository, times(1)).save(expectedConfiguredPublication);
  }

  @Test
  void givenPublicationDtoWithInvalidGenre_whenCreate_thenThrowEntityNotFoundException() {
    PublicationDto inputPublicationDto = PublicationTestDataUtil.createPublicationDto();
    Publication expectedConfiguredPublication = PublicationTestDataUtil.createPublication();

    when(genreRepository.findByName(PublicationTestDataUtil.GENRE.getName()))
        .thenReturn(Optional.empty());

    assertThatExceptionOfType(EntityNotFoundException.class)
        .isThrownBy(() -> publicationService.create(inputPublicationDto))
        .withMessage("Genre '" + PublicationTestDataUtil.GENRE.getName() + "' is not found!");

    verify(genreRepository, times(1)).findByName(PublicationTestDataUtil.GENRE.getName());
    verify(languageRepository, times(0))
        .findByName(PublicationTestDataUtil.PUBLICATION_INFO_LANGUAGE.getName());
    verify(publicationRepository, times(0)).save(expectedConfiguredPublication);
  }

  @Test
  void givenPublicationDtoWithInvalidLanguage_whenCreate_thenThrowEntityNotFoundException() {
    PublicationDto inputPublicationDto = PublicationTestDataUtil.createPublicationDto();
    Publication expectedConfiguredPublication = PublicationTestDataUtil.createPublication();

    when(genreRepository.findByName(PublicationTestDataUtil.GENRE.getName()))
        .thenReturn(Optional.of(PublicationTestDataUtil.GENRE));
    when(languageRepository.findByName(PublicationTestDataUtil.PUBLICATION_INFO_LANGUAGE.getName()))
        .thenReturn(Optional.empty());

    assertThatExceptionOfType(EntityNotFoundException.class)
        .isThrownBy(() -> publicationService.create(inputPublicationDto))
        .withMessage(
            "Language '"
                + PublicationTestDataUtil.PUBLICATION_INFO_LANGUAGE.getName()
                + "' is not found!");

    verify(genreRepository, times(1)).findByName(PublicationTestDataUtil.GENRE.getName());
    verify(languageRepository, times(1))
        .findByName(PublicationTestDataUtil.PUBLICATION_INFO_LANGUAGE.getName());
    verify(publicationRepository, times(0)).save(expectedConfiguredPublication);
  }

  @Test
  void givenExistingPublicationId_whenUpdateById_thenReturnUpdatedPublication() {
    PublicationDto publicationDto = PublicationTestDataUtil.createPublicationDto();
    Publication publication =
        PublicationMapper.INSTANCE.mapPublicationDtoToPublication(publicationDto);

    when(publicationRepository.existsById(PublicationTestDataUtil.ID)).thenReturn(true);
    when(publicationRepository.save(publication)).thenReturn(publication);

    PublicationDto updatedPublication =
        publicationService.updateById(PublicationTestDataUtil.ID, publicationDto);

    assertThat(updatedPublication, is(publicationDto));

    verify(publicationRepository, times(1)).existsById(PublicationTestDataUtil.ID);
    verify(publicationRepository, times(1)).save(publication);
  }

  @Test
  void givenNotExistingPublicationId_whenUpdateById_thenThrowEntityNotFoundException() {
    PublicationDto publicationDto = PublicationTestDataUtil.createPublicationDto();

    when(publicationRepository.existsById(PublicationTestDataUtil.ID)).thenReturn(false);

    assertThatExceptionOfType(EntityNotFoundException.class)
        .isThrownBy(() -> publicationService.updateById(PublicationTestDataUtil.ID, publicationDto))
        .withMessage("Publication with id '" + PublicationTestDataUtil.ID + "' is not found!");

    verify(publicationRepository, times(1)).existsById(PublicationTestDataUtil.ID);
    verify(publicationRepository, times(0)).save(any());
  }

  @Test
  void givenPublicationId_whenDeleteById_thenCallRepositoryMethod() {
    doNothing().when(publicationRepository).deleteById(PublicationTestDataUtil.ID);

    publicationService.deleteById(PublicationTestDataUtil.ID);

    verify(publicationRepository, times(1)).deleteById(PublicationTestDataUtil.ID);
  }
}
