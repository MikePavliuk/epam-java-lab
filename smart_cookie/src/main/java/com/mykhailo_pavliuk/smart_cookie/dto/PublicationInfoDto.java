package com.mykhailo_pavliuk.smart_cookie.dto;

import com.mykhailo_pavliuk.smart_cookie.model.enums.Language;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PublicationInfoDto {
	private Language language;
	private String title;
	private String description;

}
