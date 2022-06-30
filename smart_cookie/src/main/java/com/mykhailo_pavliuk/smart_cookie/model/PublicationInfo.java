package com.mykhailo_pavliuk.smart_cookie.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PublicationInfo {
	private Language language;
	private String title;
	private String description;
}
