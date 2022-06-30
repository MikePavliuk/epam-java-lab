package com.mykhailo_pavliuk.smart_cookie.mapper;

import com.mykhailo_pavliuk.smart_cookie.dto.UserDto;
import com.mykhailo_pavliuk.smart_cookie.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface UserMapper {
	UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

	UserDto mapUserToUserDto(User user);
	User mapUserDtoToUser(UserDto userDto);
}
