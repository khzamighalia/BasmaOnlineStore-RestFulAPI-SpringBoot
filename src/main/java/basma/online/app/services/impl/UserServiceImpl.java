package basma.online.app.services.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import basma.online.app.entities.UserEntity;
import basma.online.app.repositories.UserRepository;
import basma.online.app.services.UserService;
import basma.online.app.shared.Utils;
import basma.online.app.shared.dto.UserDto;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	UserRepository userRepository;
	
	@Autowired
	BCryptPasswordEncoder bCryptPasswordEncoder;
	
	@Autowired
	Utils util;

	
	@Override
	public UserDto CreateUser(UserDto user) {
    UserEntity checUser = userRepository.findByEmail(user.getEmail());
		
		if(checUser != null) throw new RuntimeException("User already exists");
		
		// TODO Auto-generated method stub
		UserEntity userEntity= new UserEntity(); 
		BeanUtils.copyProperties(user,userEntity);
		userEntity.setEncryptedPassword(bCryptPasswordEncoder.encode(user.getPassword()));
		userEntity.setUserId(util.generateStringId(32));
		UserEntity newUser= userRepository.save(userEntity);
		UserDto userDto= new UserDto();
		BeanUtils.copyProperties(newUser,userDto);
		return userDto;
 	}


	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

     UserEntity userEntity = userRepository.findByEmail(email);
		
		if(userEntity == null) throw new UsernameNotFoundException(email); 
		
		return new User(userEntity.getEmail(),userEntity.getEncryptedPassword(),new ArrayList<>());
	
	
	}
	
	@Override
	public UserDto getUser(String email) {
		
		UserEntity userEntity = userRepository.findByEmail(email);
		
		if(userEntity == null) throw new UsernameNotFoundException(email); 
		
		UserDto userDto = new UserDto();
		
		BeanUtils.copyProperties(userEntity, userDto);
		
		return userDto;
	}
	
	@Override
	public UserDto getUserByUserId(String userId) {

		UserEntity userEntity = userRepository.findByUserId(userId);
		
		if(userEntity == null) throw new UsernameNotFoundException(userId); 
		
		UserDto userDto = new UserDto();
		
		BeanUtils.copyProperties(userEntity, userDto);
		
		return userDto;
	}


	@Override
	public UserDto updateUser(String userId, UserDto userDto) {
   
		UserEntity userEntity = userRepository.findByUserId(userId);
		
		if(userEntity == null) throw new UsernameNotFoundException(userId); 
		
		userEntity.setFirstName(userDto.getFirstName());
		userEntity.setLastName(userDto.getLastName());
		
		UserEntity userUpdated = userRepository.save(userEntity);
		
		UserDto user = new UserDto();
		
		BeanUtils.copyProperties(userUpdated, user);
		
		return user;
		}
	
	@Override
	public void deleteUser(String userId) {
		
		UserEntity userEntity = userRepository.findByUserId(userId);
		
		if(userEntity == null) throw new UsernameNotFoundException(userId); 
		
		userRepository.delete(userEntity);
		
	}
	
	@Override
	public List<UserDto> getUsers(int page, int limit) {
		
		List<UserDto> usersDto = new ArrayList<>();
		
        Pageable pageableRequest = PageRequest.of(page, limit);
		
		Page<UserEntity> userPage = userRepository.findAll(pageableRequest); 
				
		List<UserEntity> users = userPage.getContent();
		
      for(UserEntity userEntity: users) {
			
			UserDto user = new UserDto();
			BeanUtils.copyProperties(userEntity, user);
			
			usersDto.add(user);
		}
		
		return usersDto;
	}


	

	

	
}