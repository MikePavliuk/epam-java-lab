package com.mykhailo_pavliuk.smart_cookie.mapper;

import com.mykhailo_pavliuk.smart_cookie.dto.PublicationDto;
import com.mykhailo_pavliuk.smart_cookie.dto.PublicationInfoDto;
import com.mykhailo_pavliuk.smart_cookie.model.Publication;
import com.mykhailo_pavliuk.smart_cookie.model.PublicationInfo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface PublicationMapper {
  PublicationMapper INSTANCE = Mappers.getMapper(PublicationMapper.class);

  PublicationDto mapPublicationToPublicationDto(Publication publication);

  Publication mapPublicationDtoToPublication(PublicationDto publicationDto);

  @Mapping(target = "publication", ignore = true)
  PublicationInfo mapPublicationInfoDtoToPublicationInfo(PublicationInfoDto publicationInfoDto);

  @Mapping(target = "publication", ignore = true)
  PublicationInfoDto mapPublicationInfoToPublicationInfoDto(PublicationInfo publicationInfo);
}
