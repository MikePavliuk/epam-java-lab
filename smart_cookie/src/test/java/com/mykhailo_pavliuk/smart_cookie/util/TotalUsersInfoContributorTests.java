package com.mykhailo_pavliuk.smart_cookie.util;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.mykhailo_pavliuk.smart_cookie.dto.UserStatusDto;
import com.mykhailo_pavliuk.smart_cookie.repository.UserRepository;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.actuate.info.Info;

@ExtendWith(MockitoExtension.class)
class TotalUsersInfoContributorTests {

  @InjectMocks private TotalUsersInfoContributor infoContributor;

  @Mock private UserRepository userRepository;
  @Mock private Info.Builder builder;

  @Test
  void givenOneActiveAndOneBlockedUsers_whenContribute_thenReturnMapWithUsersInfo() {
    Map<String, Long> expectedUserDetails = new HashMap<>();
    expectedUserDetails.put(UserStatusDto.ACTIVE.name(), 1L);
    expectedUserDetails.put(UserStatusDto.BLOCKED.name(), 1L);

    when(userRepository.countUsersByStatus(UserStatusDto.ACTIVE.name().toLowerCase()))
        .thenReturn(1L);
    when(userRepository.countUsersByStatus(UserStatusDto.BLOCKED.name().toLowerCase()))
        .thenReturn(1L);
    when(builder.withDetail("users", expectedUserDetails)).thenReturn(builder);

    infoContributor.contribute(builder);

    verify(userRepository, times(UserStatusDto.values().length)).countUsersByStatus(anyString());
    verify(builder, times(1)).withDetail("users", expectedUserDetails);
  }
}
