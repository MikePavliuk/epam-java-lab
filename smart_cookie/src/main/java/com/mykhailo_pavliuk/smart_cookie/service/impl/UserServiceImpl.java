package com.mykhailo_pavliuk.smart_cookie.service.impl;

import com.mykhailo_pavliuk.smart_cookie.dto.UserDto;
import com.mykhailo_pavliuk.smart_cookie.mapper.UserMapper;
import com.mykhailo_pavliuk.smart_cookie.model.User;
import com.mykhailo_pavliuk.smart_cookie.repository.UserRepository;
import com.mykhailo_pavliuk.smart_cookie.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

	private final UserRepository userRepository;

	@Override
	public UserDto getUser(String email) {
		log.info("Get user by email {}", email);
		User user = userRepository.getUser(email);
		return UserMapper.INSTANCE.mapUserToUserDto(user);
	}

	@Override
	public List<UserDto> getAllUsers() {
		log.info("Get all users");
		return userRepository.getUsers()
				.stream()
				.map(UserMapper.INSTANCE::mapUserToUserDto)
				.collect(Collectors.toList());
	}

	public UserDto createUser(UserDto userDto) {
		log.info("Create user with email {}", userDto.getEmail());
		User user = UserMapper.INSTANCE.mapUserDtoToUser(userDto);
		user = userRepository.createUser(user);
		return UserMapper.INSTANCE.mapUserToUserDto(user);
	}

	@Override
	public UserDto updateUser(String email, UserDto userDto) {
		log.info("Update user with email {}", email);
		User user = UserMapper.INSTANCE.mapUserDtoToUser(userDto);
		user = userRepository.updateUser(email, user);
		return UserMapper.INSTANCE.mapUserToUserDto(user);
	}

	@Override
	public void deleteUser(String email) {
		log.info("Delete user with email {}", email);
		userRepository.deleteUser(email);
	}

}
