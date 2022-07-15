package com.mykhailo_pavliuk.smart_cookie.mapper;

import com.mykhailo_pavliuk.smart_cookie.dto.SubscriptionDto;
import com.mykhailo_pavliuk.smart_cookie.dto.UserDto;
import com.mykhailo_pavliuk.smart_cookie.model.Subscription;
import com.mykhailo_pavliuk.smart_cookie.model.User;
import java.time.LocalDate;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

@Mapper
public interface UserMapper {
  UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

  @Mapping(target = "userDetail.user", ignore = true)
  UserDto mapUserToUserDto(User user);

  @Mapping(target = "userDetail.user", ignore = true)
  User mapUserDtoToUser(UserDto userDto);

  @Mapping(source = "subscription", target = "userId", qualifiedByName = "userId")
  @Mapping(source = "subscription", target = "publicationId", qualifiedByName = "publicationId")
  @Mapping(source = "subscription", target = "periodInMonths", qualifiedByName = "periodInMonths")
  @Mapping(source = "subscription", target = "startDate", qualifiedByName = "startDate")
  SubscriptionDto mapSubscriptionToSubscriptionDto(Subscription subscription);

  @Named("userId")
  static long subscriptionToUserId(Subscription subscription) {
    return subscription.getUser().getId();
  }

  @Named("publicationId")
  static long subscriptionToPublicationId(Subscription subscription) {
    return subscription.getPublication().getId();
  }

  @Named("periodInMonths")
  static int subscriptionToPeriodInMonths(Subscription subscription) {
    return subscription.getPeriodInMonths();
  }

  @Named("startDate")
  static LocalDate subscriptionToStartDate(Subscription subscription) {
    return subscription.getStartDate();
  }
}
