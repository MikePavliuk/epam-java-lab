package com.mykhailo_pavliuk.smart_cookie.dto;

import com.mykhailo_pavliuk.smart_cookie.dto.group.OnCreate;
import com.mykhailo_pavliuk.smart_cookie.dto.group.OnUpdate;
import com.mykhailo_pavliuk.smart_cookie.model.Genre;
import lombok.Builder;
import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;
import java.util.List;

@Data
@Builder
public class PublicationDto {

	@Null(message = "{validation.id.null}", groups = OnCreate.class)
	@NotNull(message = "{validation.id.not_null}", groups = OnUpdate.class)
	private Long id;

	@NotNull(message = "{validation.genre.not_null}")
	private Genre genre;

	@Positive(message = "{validation.publication.price_per_month}")
	private BigDecimal pricePerMonth;

	@Valid
	@NotNull(message = "{validation.publicationInfos.not_null}")
	private List<PublicationInfoDto> publicationInfos;
}
