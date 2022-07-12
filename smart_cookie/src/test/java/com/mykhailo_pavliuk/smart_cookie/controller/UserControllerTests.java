package com.mykhailo_pavliuk.smart_cookie.controller;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mykhailo_pavliuk.smart_cookie.dto.UserDetailDto;
import com.mykhailo_pavliuk.smart_cookie.dto.UserDto;
import com.mykhailo_pavliuk.smart_cookie.exception.EntityNotFoundException;
import com.mykhailo_pavliuk.smart_cookie.model.enums.ErrorType;
import com.mykhailo_pavliuk.smart_cookie.service.SubscriptionService;
import com.mykhailo_pavliuk.smart_cookie.service.UserService;
import com.mykhailo_pavliuk.smart_cookie.test.util.PublicationTestDataUtil;
import com.mykhailo_pavliuk.smart_cookie.test.util.UserTestDataUtil;
import java.math.BigDecimal;
import java.util.Arrays;
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
@WebMvcTest(UserController.class)
class UserControllerTests {

  @MockBean private UserService userService;
  @MockBean private SubscriptionService subscriptionService;

  @Autowired private MockMvc mockMvc;
  @Autowired private ObjectMapper objectMapper;

  @Test
  void givenExistingUserId_whenGetById_thenReturnUserDto() throws Exception {
    UserDto userDto = UserTestDataUtil.creatUserDto();

    when(userService.getById(userDto.getId())).thenReturn(userDto);

    mockMvc
        .perform(get("/api/v1/users/" + UserTestDataUtil.ID))
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$.id").value(UserTestDataUtil.ID));
  }

  @Test
  void givenNotExistingUserId_whenGetById_thenReturnValidationErrorJson() throws Exception {
    UserDto userDto = UserTestDataUtil.creatUserDto();

    when(userService.getById(userDto.getId())).thenThrow(EntityNotFoundException.class);

    mockMvc
        .perform(get("/api/v1/users/" + UserTestDataUtil.ID))
        .andDo(print())
        .andExpect(status().is5xxServerError())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$.errorType").value(ErrorType.VALIDATION_ERROR_TYPE.name()));

    verify(userService, times(1)).getById(UserTestDataUtil.ID);
  }

  @Test
  void givenPageable_whenGetAll_thenReturnPageWithUserDto() throws Exception {
    int page = 0;
    int size = 2;
    UserDto userDto1 = UserTestDataUtil.creatUserDto();
    UserDto userDto2 = UserTestDataUtil.creatUserDto();
    userDto2.setId(2L);
    Pageable pageable = PageRequest.of(page, size);
    Page<UserDto> userDtoPage = new PageImpl<>(Arrays.asList(userDto1, userDto2), pageable, 2);

    when(userService.getAll(pageable)).thenReturn(userDtoPage);

    mockMvc
        .perform(
            get("/api/v1/users")
                .param("page", String.valueOf(page))
                .param("size", String.valueOf(size)))
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$.pageable.pageNumber").value(page))
        .andExpect(jsonPath("$.pageable.pageSize").value(size))
        .andExpect(jsonPath("$.content[0].id").value(userDto1.getId()))
        .andExpect(jsonPath("$.content[1].id").value(userDto2.getId()));

    verify(userService, times(1)).getAll(pageable);
  }

  @Test
  void givenValidUserDto_whenCreate_thenReturnCreatedUser() throws Exception {
    UserDto inputUser = UserTestDataUtil.creatUserDto();
    inputUser.setId(null);

    UserDto returnedUser = UserTestDataUtil.creatUserDto();

    when(userService.create(inputUser)).thenReturn(returnedUser);

    mockMvc
        .perform(
            post("/api/v1/users")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(inputUser)))
        .andDo(print())
        .andExpect(status().isCreated())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$.id").value(returnedUser.getId()));

    verify(userService, times(1)).create(inputUser);
  }

  @Test
  void givenInvalidUserDto_whenCreate_thenReturnErrorListJson() throws Exception {
    UserDto inputUser = UserTestDataUtil.creatUserDto();
    inputUser.setEmail("email");
    inputUser.setUserDetail(UserDetailDto.builder().firstName("mike").lastName("pavliuk").build());

    when(userService.create(inputUser)).thenReturn(inputUser);

    mockMvc
        .perform(
            post("/api/v1/users")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(inputUser)))
        .andDo(print())
        .andExpect(status().isBadRequest())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$[0].errorType").value(ErrorType.VALIDATION_ERROR_TYPE.name()))
        .andExpect(jsonPath("$[1].errorType").value(ErrorType.VALIDATION_ERROR_TYPE.name()))
        .andExpect(jsonPath("$[2].errorType").value(ErrorType.VALIDATION_ERROR_TYPE.name()))
        .andExpect(jsonPath("$[3].errorType").value(ErrorType.VALIDATION_ERROR_TYPE.name()));

    verify(userService, times(0)).create(inputUser);
  }

  @Test
  void givenValidUserDto_whenUpdateById_thenReturnUpdatedUser() throws Exception {
    UserDto inputUser = UserTestDataUtil.createValidUserDto();

    when(userService.updateById(UserTestDataUtil.ID, inputUser)).thenReturn(inputUser);

    mockMvc
        .perform(
            patch("/api/v1/users/" + UserTestDataUtil.ID)
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(inputUser)))
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$.id").value(inputUser.getId()));

    verify(userService, times(1)).updateById(UserTestDataUtil.ID, inputUser);
  }

  @Test
  void givenUserId_whenDeleteById_thenDeleteAndReturnNothing() throws Exception {
    doNothing().when(userService).deleteById(UserTestDataUtil.ID);

    mockMvc
        .perform(delete("/api/v1/users/" + UserTestDataUtil.ID))
        .andDo(print())
        .andExpect(status().isNoContent());

    verify(userService, times(1)).deleteById(UserTestDataUtil.ID);
  }

  @Test
  void givenEmail_whenGetByEmail_thenReturnUserDto() throws Exception {
    UserDto userDto = UserTestDataUtil.createValidUserDto();

    when(userService.getByEmail(UserTestDataUtil.EMAIL)).thenReturn(userDto);

    mockMvc
        .perform(get("/api/v1/users/search").param("email", UserTestDataUtil.EMAIL))
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$.id").value(UserTestDataUtil.ID));

    verify(userService, times(1)).getByEmail(UserTestDataUtil.EMAIL);
  }

  @Test
  void givenParamsForSubscription_whenAddSubscriptionToUser_thenReturnUserDto() throws Exception {
    UserDto userDto = UserTestDataUtil.createValidUserDto();

    when(subscriptionService.addSubscriptionToUser(
            UserTestDataUtil.ID, PublicationTestDataUtil.ID, 1))
        .thenReturn(userDto);

    mockMvc
        .perform(
            put("/api/v1/users/"
                    + UserTestDataUtil.ID
                    + "/publications/"
                    + PublicationTestDataUtil.ID)
                .param("periodInMonths", String.valueOf(1)))
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$.id").value(UserTestDataUtil.ID));

    verify(subscriptionService, times(1))
        .addSubscriptionToUser(UserTestDataUtil.ID, PublicationTestDataUtil.ID, 1);
  }

  @Test
  void givenParamsForAddingFunds_whenAddFundsToUser_thenReturnUserDto() throws Exception {
    UserDto userDto = UserTestDataUtil.createValidUserDto();
    BigDecimal amount = BigDecimal.TEN;

    when(userService.addFunds(UserTestDataUtil.ID, amount)).thenReturn(userDto);

    mockMvc
        .perform(
            patch("/api/v1/users/" + UserTestDataUtil.ID + "/add-funds")
                .param("amount", String.valueOf(amount)))
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$.id").value(UserTestDataUtil.ID));

    verify(userService, times(1)).addFunds(UserTestDataUtil.ID, amount);
  }
}
