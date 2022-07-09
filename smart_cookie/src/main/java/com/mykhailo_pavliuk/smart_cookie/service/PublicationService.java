package com.mykhailo_pavliuk.smart_cookie.service;

import com.mykhailo_pavliuk.smart_cookie.dto.PublicationDto;
import java.util.List;

public interface PublicationService extends CrudService<PublicationDto, Long> {

  List<PublicationDto> getPublicationsByUserId(long userId);
}
