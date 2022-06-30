package com.mykhailo_pavliuk.smart_cookie.model;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
@Builder
public class Publication {
	private Long id;
    private Genre genre;
    private BigDecimal pricePerMonth;
	private List<PublicationInfo> publicationInfos;
}
