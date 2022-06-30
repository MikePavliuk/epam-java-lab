package com.mykhailo_pavliuk.smart_cookie.dto;

import com.mykhailo_pavliuk.smart_cookie.model.Genre;
import com.mykhailo_pavliuk.smart_cookie.model.PublicationInfo;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
@Builder
public class PublicationDto {
	private Long id;
	private Genre genre;
	private BigDecimal pricePerMonth;
	private List<PublicationInfo> publicationInfos;
}
