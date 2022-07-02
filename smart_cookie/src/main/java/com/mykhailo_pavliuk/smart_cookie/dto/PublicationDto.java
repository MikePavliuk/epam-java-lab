package com.mykhailo_pavliuk.smart_cookie.dto;

import com.mykhailo_pavliuk.smart_cookie.model.Genre;
import lombok.Builder;
import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;
import java.util.List;

@Data
@Builder
public class PublicationDto {

	private Long id;

	@NotNull
	private Genre genre;

	@Positive
	private BigDecimal pricePerMonth;

	@Valid
	private List<PublicationInfoDto> publicationInfos;
}
