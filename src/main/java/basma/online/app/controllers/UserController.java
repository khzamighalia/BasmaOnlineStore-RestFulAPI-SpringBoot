package basma.online.app.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;  
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import basma.online.app.requests.UserRequest;
import basma.online.app.responses.UserResponse;
import basma.online.app.services.UserService;
import basma.online.app.shared.dto.UserDto;


@RestController
@RequestMapping("/users")
public class UserController {
	
	@Autowired
	UserService userService;

	@GetMapping(path="/{id}")
	public UserResponse getUser(@PathVariable String id) {
        
		UserDto userDto = userService.getUserByUserId(id);
		
		UserResponse userResponse = new UserResponse();
		
		BeanUtils.copyProperties(userDto, userResponse);
		
		return userResponse;		
	}
	
	@GetMapping()
	public List<UserResponse> getAllUsers(@RequestParam(value="page", defaultValue = "0") int page,@RequestParam(value="limit", defaultValue = "4")  int limit ,@RequestParam(value="search", defaultValue = "") String search,@RequestParam(value="status", defaultValue = "1") int status) {
		
		List<UserResponse> usersResponse = new ArrayList<>();
		
		List<UserDto> users = userService.getUsers(page, limit);
		
		for(UserDto userDto: users) {
			
			UserResponse user = new UserResponse();
			BeanUtils.copyProperties(userDto, user);
			
			usersResponse.add(user);
		}
		
		return usersResponse;
	}
	
	
	@PostMapping
	public ResponseEntity<UserResponse> createUser(@RequestBody UserRequest userRequest) {
      UserDto userDto=new UserDto();
      BeanUtils.copyProperties(userRequest,userDto);
      UserDto createUser= userService.CreateUser(userDto);
      UserResponse userResponse= new UserResponse();
      BeanUtils.copyProperties(createUser,userResponse);

		return new ResponseEntity<UserResponse>(userResponse, HttpStatus.CREATED);
      
	}
	
	@PutMapping(path="/{id}")
	public ResponseEntity<UserResponse> updateUser(@PathVariable String id,@RequestBody UserRequest userRequest) {

		UserDto userDto = new UserDto();
		
		BeanUtils.copyProperties(userRequest, userDto);
		
		UserDto updateUser = userService.updateUser(id, userDto);
		
		UserResponse userResponse = new UserResponse();
		
		BeanUtils.copyProperties(updateUser, userResponse);
		
		return new ResponseEntity<UserResponse>(userResponse, HttpStatus.ACCEPTED);
	}
	
	@DeleteMapping(path="/{id}")
	public ResponseEntity<Object> deleteUser(@PathVariable String id) {
		
		userService.deleteUser(id);
		
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}


}
