package com.mykhailo_pavliuk.smart_cookie.test.util;

import com.mykhailo_pavliuk.smart_cookie.dto.PublicationDto;
import com.mykhailo_pavliuk.smart_cookie.mapper.PublicationMapper;
import com.mykhailo_pavliuk.smart_cookie.model.Genre;
import com.mykhailo_pavliuk.smart_cookie.model.Language;
import com.mykhailo_pavliuk.smart_cookie.model.Publication;
import com.mykhailo_pavliuk.smart_cookie.model.PublicationInfo;
import java.math.BigDecimal;
import java.util.HashSet;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class PublicationTestDataUtil {

  public static final Long ID = 1L;
  public static final Genre GENRE = createFictionGenre();
  public static final BigDecimal PRICE_PER_MONTH = BigDecimal.valueOf(10.99);
  public static final Long PUBLICATION_INFO_ID = 1L;
  public static final Language PUBLICATION_INFO_LANGUAGE = createEnglishLanguage();
  public static final String PUBLICATION_INFO_TITLE = "Publication title";
  public static final String PUBLICATION_INFO_DESCRIPTION = "Publication description";

  public static Publication createPublication() {
    return Publication.builder()
        .id(ID)
        .genre(GENRE)
        .pricePerMonth(PRICE_PER_MONTH)
        .publicationInfos(
            new HashSet<>() {
              {
                add(
                    PublicationInfo.builder()
                        .id(PUBLICATION_INFO_ID)
                        .title(PUBLICATION_INFO_TITLE)
                        .description(PUBLICATION_INFO_DESCRIPTION)
                        .language(PUBLICATION_INFO_LANGUAGE)
                        .build());
              }
            })
        .build();
  }

  private static Genre createFictionGenre() {
    return Genre.builder().id(1L).name("fiction").build();
  }

  private static Language createEnglishLanguage() {
    return Language.builder().id(1L).name("english").build();
  }

  public static PublicationDto createPublicationDto() {
    return PublicationMapper.INSTANCE.mapPublicationToPublicationDto(createPublication());
  }
}
