package basma.online.app.services;

import java.util.List;

import org.springframework.security.core.userdetails.UserDetailsService;

import basma.online.app.shared.dto.UserDto;


public interface UserService extends UserDetailsService {

	UserDto CreateUser(UserDto userDto);
	UserDto getUser(String email);
	UserDto getUserByUserId(String userId);
	UserDto updateUser(String userId, UserDto userDto);
	public void deleteUser(String userId);
	public List<UserDto> getUsers(int page, int limit);
}