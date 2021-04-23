package basma.online.app;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import basma.online.app.services.UserService;
import basma.online.app.shared.dto.UserDto;

@SpringBootTest
class ApplicationTests {
	
	@Autowired
	private UserService userService;
	
	@Test
	void contextLoads() {
	}
	
	@Test
	void listUsers() {
		
		List<UserDto> list = userService.getUsers(0, 4);
		for(UserDto user : list) {
			System.out.println(user);
		}
	}
	
		

}

	