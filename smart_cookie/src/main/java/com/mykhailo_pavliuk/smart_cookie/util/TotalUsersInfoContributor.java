package com.mykhailo_pavliuk.smart_cookie.util;

import com.mykhailo_pavliuk.smart_cookie.model.enums.Status;
import com.mykhailo_pavliuk.smart_cookie.repository.UserRepository;
import java.util.HashMap;
import java.util.Map;
import lombok.AllArgsConstructor;
import org.springframework.boot.actuate.info.Info;
import org.springframework.boot.actuate.info.InfoContributor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class TotalUsersInfoContributor implements InfoContributor {

  private final UserRepository userRepository;

  @Override
  public void contribute(Info.Builder builder) {
    Map<String, Long> userDetails = new HashMap<>();
    for (Status status : Status.values()) {
      userDetails.put(status.name(), userRepository.countByStatus(status));
    }

    builder.withDetail("users", userDetails);
  }
}
