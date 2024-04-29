package clear.solution.test.task.controller;

import clear.solution.test.task.dto.UserDto;
import clear.solution.test.task.dto.request.UserPatchRequestDto;
import clear.solution.test.task.dto.request.UserRequestDto;
import clear.solution.test.task.exception.DateRangeException;
import clear.solution.test.task.exception.UserNotFoundException;
import clear.solution.test.task.model.User;
import clear.solution.test.task.service.UserService;
import clear.solution.test.task.service.mapper.UserMapper;
import clear.solution.test.task.utils.SharedTestResources;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.Collections;

import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(UserController.class)
class UserControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ObjectMapper objectMapper;

	@MockBean
	private UserService userService;

	@MockBean
	private UserMapper userMapper;


	@Test
	public void givenValidUserData_whenAddsUser_thenOk() throws Exception {
		UserRequestDto requestDto = SharedTestResources.getMockValidUserRequestDto();
		UserDto userDto = SharedTestResources.getMockValidUserDto();

		when(userMapper.toUser(any(UserRequestDto.class))).thenReturn(new User());
		when(userService.add(any(User.class))).thenReturn(new User());
		when(userMapper.toDto(any(User.class))).thenReturn(userDto);

		mockMvc.perform(post("/users")
						.contentType(MediaType.APPLICATION_JSON)
						.content(objectMapper.writeValueAsString(requestDto)))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.id", is(userDto.getId().intValue())))
				.andExpect(jsonPath("$.email", is(userDto.getEmail())))
				.andExpect(jsonPath("$.firstName", is(userDto.getFirstName())))
				.andExpect(jsonPath("$.lastName", is(userDto.getLastName())))
				.andExpect(jsonPath("$.birthDate", is(userDto.getBirthDate().toString())))
				.andReturn();
	}

	@Test
	public void givenInvalidUserData_whenAddsUser_thenBadRequest() throws Exception {
		UserRequestDto requestDto = SharedTestResources.getMockInvalidUserRequestDto();

		mockMvc.perform(post("/users")
						.contentType(MediaType.APPLICATION_JSON)
						.content(objectMapper.writeValueAsString(requestDto)))
				.andExpect(status().isBadRequest())
				.andExpect(jsonPath("$.statusCode", is(HttpStatus.BAD_REQUEST.name())))
				.andExpect(jsonPath("$.errorMessage", hasSize(5)))
				.andExpect(jsonPath("$.errorMessage",
						containsInAnyOrder("Invalid email", "First name can't be null or blank",
								"Last name can't be null or blank", "Your age should be more than 18 years old",
								"Invalid phone number")))
				.andReturn();

	}

	@Test
	public void givenValidUserData_whenUpdatesUser_thenOk() throws Exception {
		UserRequestDto requestDto = SharedTestResources.getMockUpdatedUserRequestDto();
		UserDto updatedUserDto = SharedTestResources.getMockUpdatedUserDto();

		when(userMapper.toUser(any(UserRequestDto.class))).thenReturn(new User());
		when(userService.update(eq(SharedTestResources.VALID_USER_ID), any(User.class))).thenReturn(new User());
		when(userMapper.toDto(any(User.class))).thenReturn(updatedUserDto);

		mockMvc.perform(put("/users/{id}", SharedTestResources.VALID_USER_ID)
						.contentType(MediaType.APPLICATION_JSON)
						.content(objectMapper.writeValueAsString(requestDto)))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.id", is(updatedUserDto.getId().intValue())))
				.andExpect(jsonPath("$.firstName", is(updatedUserDto.getFirstName())))
				.andReturn();
	}

	@Test
	public void givenValidUserId_whenDeletesUser_thenNoContent() throws Exception {

		mockMvc.perform(delete("/users/{id}", SharedTestResources.VALID_USER_ID))
				.andExpect(status().isNoContent())
				.andReturn();
	}

	@Test
	public void givenInvalidUserId_whenDeletesUser_thenNotFound()  throws Exception {
		doThrow(new UserNotFoundException("There is no user with id : " + SharedTestResources.INVALID_USER_ID))
				.when(userService).delete(SharedTestResources.INVALID_USER_ID);

		mockMvc.perform(delete("/users/{id}", SharedTestResources.INVALID_USER_ID))
				.andExpect(status().isNotFound())
				.andExpect(jsonPath("$.statusCode", is(HttpStatus.NOT_FOUND.name())))
				.andExpect(jsonPath("$.errorMessage", hasSize(1)))
				.andExpect(jsonPath("$.errorMessage[0]", is("There is no user with id : " +
						SharedTestResources.INVALID_USER_ID)))
				.andReturn();
	}

	@Test
	public void givenValidUserPatchData_whenPatchesUser_thenOk() throws Exception {
		UserPatchRequestDto requestDto = SharedTestResources.getMockValidUserPatchRequestDto();
		UserDto patchedUserDto = SharedTestResources.getMockUpdatedUserDto();

		when(userService.findUserById(eq(SharedTestResources.VALID_USER_ID))).thenReturn(new User());
		when(userService.update(eq(SharedTestResources.VALID_USER_ID), any(User.class))).thenReturn(new User());
		when(userMapper.toDto(any(User.class))).thenReturn(patchedUserDto);

		mockMvc.perform(patch("/users/{id}", SharedTestResources.VALID_USER_ID)
						.contentType(MediaType.APPLICATION_JSON)
						.content(objectMapper.writeValueAsString(requestDto)))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.id", is(patchedUserDto.getId().intValue())))
				.andExpect(jsonPath("$.firstName", is(patchedUserDto.getFirstName())))
				.andReturn();
	}

	@Test
	public void givenInvalidUserPatchData_whenPatchesUser_thenBadRequest() throws Exception {
		UserPatchRequestDto requestDto = SharedTestResources.getMockInvalidUserPatchRequestDto();

		mockMvc.perform(patch("/users/{id}", SharedTestResources.VALID_USER_ID)
						.contentType(MediaType.APPLICATION_JSON)
						.content(objectMapper.writeValueAsString(requestDto)))
				.andExpect(status().isBadRequest())
				.andExpect(jsonPath("$.statusCode", is(HttpStatus.BAD_REQUEST.name())))
				.andExpect(jsonPath("$.errorMessage", hasSize(1)))
				.andExpect(jsonPath("$.errorMessage[0]", is("Invalid phone number")))
				.andReturn();
	}

	@Test
	public void givenValidBirthDateRange_whenGetsUsersByBirthDateRange_thenOk() throws Exception {
		LocalDate from = SharedTestResources.FROM_DATE;
		LocalDate to = SharedTestResources.TO_DATE;
		int page = SharedTestResources.PAGE;
		int size = SharedTestResources.SIZE;

		Page<User> userPage = new PageImpl<>(Collections.emptyList());
		when(userService.getUsersByBirthDate(from, to, PageRequest.of(page, size))).thenReturn(userPage);

		mockMvc.perform(get("/users")
						.param("from", from.toString())
						.param("to", to.toString())
						.param("page", String.valueOf(page))
						.param("size", String.valueOf(size)))
				.andExpect(status().isOk());

		verify(userService).getUsersByBirthDate(from, to, PageRequest.of(page, size));
	}

	@Test
	public void givenInvalidBirthDateRange_whenGetsUsersByBirthDateRange_thenBadRequest() throws Exception {
		LocalDate from = SharedTestResources.INVALID_FROM_DATE;
		LocalDate to = SharedTestResources.TO_DATE;
		int page = SharedTestResources.PAGE;
		int size = SharedTestResources.SIZE;

		when(userService.getUsersByBirthDate(from, to, PageRequest.of(page, size)))
				.thenThrow(new DateRangeException("Invalid range of birth dates"));

		mockMvc.perform(get("/users")
						.param("from", from.toString())
						.param("to", to.toString())
						.param("page", String.valueOf(page))
						.param("size", String.valueOf(size)))
				.andExpect(status().isBadRequest())
				.andExpect(jsonPath("$.statusCode", is(HttpStatus.BAD_REQUEST.name())))
				.andExpect(jsonPath("$.errorMessage", hasSize(1)))
				.andExpect(jsonPath("$.errorMessage[0]", is("Invalid range of birth dates")));

		verify(userService).getUsersByBirthDate(from, to, PageRequest.of(page, size));
	}
}
