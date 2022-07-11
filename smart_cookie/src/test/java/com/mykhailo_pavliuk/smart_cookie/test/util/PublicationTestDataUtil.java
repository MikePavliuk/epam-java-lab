package com.mykhailo_pavliuk.smart_cookie.test.util;

import com.mykhailo_pavliuk.smart_cookie.model.Genre;
import com.mykhailo_pavliuk.smart_cookie.model.Publication;
import java.math.BigDecimal;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class PublicationTestDataUtil {

  public static final Long ID = 1L;
  public static final Genre GENRE = Genre.builder().id(1L).name("fiction").build();
  public static final BigDecimal PRICE_PER_MONTH = BigDecimal.valueOf(10.99);

  public static Publication createPublication() {
    return Publication.builder().id(ID).genre(GENRE).pricePerMonth(PRICE_PER_MONTH).build();
  }
}
