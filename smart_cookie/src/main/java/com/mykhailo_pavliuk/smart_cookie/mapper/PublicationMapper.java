package com.mykhailo_pavliuk.smart_cookie.mapper;

import com.mykhailo_pavliuk.smart_cookie.dto.PublicationDto;
import com.mykhailo_pavliuk.smart_cookie.model.Publication;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface PublicationMapper {
	PublicationMapper INSTANCE = Mappers.getMapper(PublicationMapper.class);

	PublicationDto mapPublicationToPublicationDto(Publication publication);
	Publication mapPublicationDtoToPublication(PublicationDto publicationDto);
}
