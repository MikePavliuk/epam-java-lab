package com.mykhailo_pavliuk.smart_cookie.dto;

import com.mykhailo_pavliuk.smart_cookie.model.Genre;
import java.math.BigDecimal;
import java.util.List;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PublicationDto {

  private Long id;

  private Genre genre;

  private BigDecimal pricePerMonth;

  private List<PublicationInfoDto> publicationInfos;
}
