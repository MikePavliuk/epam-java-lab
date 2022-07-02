package com.mykhailo_pavliuk.smart_cookie.dto;

import com.mykhailo_pavliuk.smart_cookie.model.enums.Language;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Data
@Builder
public class PublicationInfoDto {

	@NotNull(message = "{validation.language.not_null}")
	private Language language;

	@Pattern(regexp = ".{2,100}",
			message = "{validation.publication.title}")
	private String title;

	@Pattern(regexp = ".{10,255}",
			message = "{validation.publication.description}")
	private String description;

}
