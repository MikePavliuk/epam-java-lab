package com.mykhailo_pavliuk.smart_cookie.repository;

import com.mykhailo_pavliuk.smart_cookie.model.Subscription;
import java.time.LocalDate;
import java.util.List;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface SubscriptionRepository extends CrudRepository<Subscription, Long> {

  @Modifying
  void subscribeUserToPublication(
      long userId, long publicationId, int periodInMonths, LocalDate startDate);

  List<Subscription> getAllSubscriptionsByUserId(@Param("userId") long userId);
}
