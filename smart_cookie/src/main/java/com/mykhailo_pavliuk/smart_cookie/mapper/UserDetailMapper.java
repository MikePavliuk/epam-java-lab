package com.mykhailo_pavliuk.smart_cookie.mapper;

import com.mykhailo_pavliuk.smart_cookie.dto.UserDetailDto;
import com.mykhailo_pavliuk.smart_cookie.model.UserDetail;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface UserDetailMapper {
	UserDetailMapper INSTANCE = Mappers.getMapper(UserDetailMapper.class);

	UserDetailDto mapUserDetailToUserDetailDto(UserDetail userDetail);
	UserDetail mapUserDetailDtoToUserDetail(UserDetailDto userDetailDto);
}
