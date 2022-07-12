package com.mykhailo_pavliuk.smart_cookie.controller;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mykhailo_pavliuk.smart_cookie.dto.PublicationDto;
import com.mykhailo_pavliuk.smart_cookie.dto.PublicationInfoDto;
import com.mykhailo_pavliuk.smart_cookie.model.enums.ErrorType;
import com.mykhailo_pavliuk.smart_cookie.service.PublicationService;
import com.mykhailo_pavliuk.smart_cookie.test.util.PublicationTestDataUtil;
import com.mykhailo_pavliuk.smart_cookie.test.util.UserTestDataUtil;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

@ExtendWith(SpringExtension.class)
@WebMvcTest(PublicationController.class)
class PublicationControllerTests {

  @MockBean private PublicationService publicationService;

  @Autowired private MockMvc mockMvc;
  @Autowired private ObjectMapper objectMapper;

  @Test
  void givenExistingPublicationId_whenGetById_thenReturnPublicationDto() throws Exception {
    PublicationDto publicationDto = PublicationTestDataUtil.createPublicationDto();

    when(publicationService.getById(publicationDto.getId())).thenReturn(publicationDto);

    mockMvc
        .perform(get("/api/v1/publications/" + PublicationTestDataUtil.ID))
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$.id").value(PublicationTestDataUtil.ID));
  }

  @Test
  void givenPageable_whenGetAll_thenReturnPageWithUserDto() throws Exception {
    int page = 0;
    int size = 2;
    PublicationDto publicationDto1 = PublicationTestDataUtil.createPublicationDto();
    PublicationDto publicationDto2 = PublicationTestDataUtil.createPublicationDto();
    publicationDto2.setId(2L);
    Pageable pageable = PageRequest.of(page, size);
    Page<PublicationDto> publicationDtoPage =
        new PageImpl<>(Arrays.asList(publicationDto1, publicationDto2), pageable, 2);

    when(publicationService.getAll(pageable)).thenReturn(publicationDtoPage);

    mockMvc
        .perform(
            get("/api/v1/publications")
                .param("page", String.valueOf(page))
                .param("size", String.valueOf(size)))
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$.pageable.pageNumber").value(page))
        .andExpect(jsonPath("$.pageable.pageSize").value(size))
        .andExpect(jsonPath("$.content[0].id").value(publicationDto1.getId()))
        .andExpect(jsonPath("$.content[1].id").value(publicationDto2.getId()));

    verify(publicationService, times(1)).getAll(pageable);
  }

  @Test
  void givenValidPublicationDto_whenCreate_thenReturnCreatedPublication() throws Exception {
    PublicationDto inputPublication = PublicationTestDataUtil.createPublicationDto();
    inputPublication.setId(null);
    Set<PublicationInfoDto> publicationInfoDtos = new HashSet<>();
    publicationInfoDtos.add(
        PublicationInfoDto.builder()
            .id(null)
            .title(PublicationTestDataUtil.PUBLICATION_INFO_TITLE)
            .description(PublicationTestDataUtil.PUBLICATION_INFO_DESCRIPTION)
            .language(PublicationTestDataUtil.PUBLICATION_INFO_LANGUAGE)
            .build());
    inputPublication.setPublicationInfos(publicationInfoDtos);

    PublicationDto returnedPublication = PublicationTestDataUtil.createPublicationDto();

    when(publicationService.create(inputPublication)).thenReturn(returnedPublication);

    mockMvc
        .perform(
            post("/api/v1/publications")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(inputPublication)))
        .andDo(print())
        .andExpect(status().isCreated())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$.id").value(PublicationTestDataUtil.ID));

    verify(publicationService, times(1)).create(inputPublication);
  }

  @Test
  void givenInvalidPublicationDto_whenCreate_thenReturnErrorListJson() throws Exception {
    PublicationDto inputPublication = PublicationTestDataUtil.createPublicationDto();
    inputPublication.setPublicationInfos(null);

    when(publicationService.create(inputPublication)).thenReturn(inputPublication);

    mockMvc
        .perform(
            post("/api/v1/publications")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(inputPublication)))
        .andDo(print())
        .andExpect(status().isBadRequest())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$[0].errorType").value(ErrorType.VALIDATION_ERROR_TYPE.name()))
        .andExpect(jsonPath("$[1].errorType").value(ErrorType.VALIDATION_ERROR_TYPE.name()));

    verify(publicationService, times(0)).create(inputPublication);
  }

  @Test
  void givenValidPublicationDto_whenUpdateById_thenReturnUpdatedPublication() throws Exception {
    PublicationDto inputPublication = PublicationTestDataUtil.createPublicationDto();

    when(publicationService.updateById(UserTestDataUtil.ID, inputPublication))
        .thenReturn(inputPublication);

    mockMvc
        .perform(
            patch("/api/v1/publications/" + PublicationTestDataUtil.ID)
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(inputPublication)))
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$.id").value(inputPublication.getId()));

    verify(publicationService, times(1)).updateById(PublicationTestDataUtil.ID, inputPublication);
  }

  @Test
  void givenUserId_whenDeleteById_thenDeleteAndReturnNothing() throws Exception {
    doNothing().when(publicationService).deleteById(PublicationTestDataUtil.ID);

    mockMvc
        .perform(delete("/api/v1/publications/" + PublicationTestDataUtil.ID))
        .andDo(print())
        .andExpect(status().isNoContent());

    verify(publicationService, times(1)).deleteById(PublicationTestDataUtil.ID);
  }
}
