package com.mykhailo_pavliuk.smart_cookie.api;

import com.mykhailo_pavliuk.smart_cookie.dto.PublicationDto;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.RequestMapping;

@Api(tags = "Publication management API")
@RequestMapping("/api/v1/publications")
public interface PublicationApi extends CrudApi<PublicationDto, Long> {}
