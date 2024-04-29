package clear.solution.test.task.service.mapper;

import clear.solution.test.task.dto.UserDto;
import clear.solution.test.task.dto.request.UserRequestDto;
import clear.solution.test.task.model.User;
import clear.solution.test.task.utils.SharedTestResources;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class UserMapperTest {

	@Autowired
	private UserMapper userMapper;

	@Test
	public void givenUserRequestDtoToUser_whenMaps_thenCorrect() {
		UserRequestDto userRequestDto = SharedTestResources.getMockValidUserRequestDto();

		User user = userMapper.toUser(userRequestDto);

		Assertions.assertEquals(userRequestDto.getEmail(), user.getEmail());
		Assertions.assertEquals(userRequestDto.getFirstName(), user.getFirstName());
		Assertions.assertEquals(userRequestDto.getLastName(), user.getLastName());
		Assertions.assertEquals(userRequestDto.getBirthDate(), user.getBirthDate());
		Assertions.assertEquals(userRequestDto.getAddress(), user.getAddress());
		Assertions.assertEquals(userRequestDto.getPhoneNumber(), user.getPhoneNumber());
	}

	@Test
	public void givenUserToUserDto_whenMaps_thenCorrect() {
		User user = SharedTestResources.getMockValidUser();

		UserDto userDto = userMapper.toDto(user);

		Assertions.assertEquals(user.getEmail(), userDto.getEmail());
		Assertions.assertEquals(user.getFirstName(), userDto.getFirstName());
		Assertions.assertEquals(user.getLastName(), userDto.getLastName());
		Assertions.assertEquals(user.getBirthDate(), userDto.getBirthDate());
		Assertions.assertEquals(user.getAddress(), userDto.getAddress());
		Assertions.assertEquals(user.getPhoneNumber(), userDto.getPhoneNumber());
	}
}
