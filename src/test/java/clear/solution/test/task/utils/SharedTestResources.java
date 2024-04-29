package clear.solution.test.task.utils;

import clear.solution.test.task.dto.UserDto;
import clear.solution.test.task.dto.request.UserPatchRequestDto;
import clear.solution.test.task.dto.request.UserRequestDto;
import clear.solution.test.task.model.User;

import java.time.LocalDate;

public class SharedTestResources {
	public static final Long VALID_USER_ID = 1L;
	public static final Long INVALID_USER_ID = 0L;
	public static final String NULL_EMAIL = null;
	public static final String VALID_EMAIL = "example@exm.com";
	public static final String VALID_FIRST_NAME = "Bob";
	public static final String UPDATED_FIRST_NAME = "Alice";
	public static final String VALID_LAST_NAME = "Bobson";
	public static final LocalDate VALID_BIRTH_DATE = LocalDate.of(1999, 5, 14);
	public static final String INVALID_EMAIL = "exam.com";
	public static final String BLANK_FIRST_NAME = " ";
	public static final String BLANK_LAST_NAME = null;
	public static final LocalDate INVALID_BIRTH_DATE = LocalDate.of(2010, 5, 14);
	public static final String VALID_ADDRESS = "Kyiv";
	public static final String INVALID_PHONE_NUMBER = "+370000000";
	public static final String VALID_PHONE_NUMBER = "+380000000000";
	public static final String NULL_PHONE_NUMBER = null;
	public static final LocalDate NULL_BIRTH_DATE = null;
	public static final LocalDate FROM_DATE = LocalDate.of(2000, 1, 1);
	public static final LocalDate INVALID_FROM_DATE = LocalDate.of(2001, 1, 1);
	public static final LocalDate TO_DATE = LocalDate.of(2000, 12, 31);
	public static final int PAGE = 0;
	public static final int SIZE = 10;

	public static UserDto getMockValidUserDto() {
		return UserDto.builder()
				.id(VALID_USER_ID)
				.email(VALID_EMAIL)
				.firstName(VALID_FIRST_NAME)
				.lastName(VALID_LAST_NAME)
				.phoneNumber(VALID_PHONE_NUMBER)
				.address(VALID_ADDRESS)
				.birthDate(VALID_BIRTH_DATE)
				.build();
	}

	public static UserDto getMockUpdatedUserDto() {
		return UserDto.builder()
				.id(VALID_USER_ID)
				.email(VALID_EMAIL)
				.firstName(UPDATED_FIRST_NAME)
				.lastName(VALID_LAST_NAME)
				.phoneNumber(VALID_PHONE_NUMBER)
				.address(VALID_ADDRESS)
				.birthDate(VALID_BIRTH_DATE)
				.build();
	}

	public static UserRequestDto getMockValidUserRequestDto() {
		return UserRequestDto.builder()
				.email(VALID_EMAIL)
				.firstName(VALID_FIRST_NAME)
				.lastName(VALID_LAST_NAME)
				.phoneNumber(VALID_PHONE_NUMBER)
				.address(VALID_ADDRESS)
				.birthDate(VALID_BIRTH_DATE)
				.build();
	}

	public static UserRequestDto getMockInvalidUserRequestDto() {
		return UserRequestDto.builder()
				.email(INVALID_EMAIL)
				.firstName(BLANK_FIRST_NAME)
				.lastName(BLANK_LAST_NAME)
				.phoneNumber(INVALID_PHONE_NUMBER)
				.birthDate(INVALID_BIRTH_DATE)
				.build();
	}

	public static UserRequestDto getMockUpdatedUserRequestDto() {
		return UserRequestDto.builder()
				.email(VALID_EMAIL)
				.firstName(UPDATED_FIRST_NAME)
				.lastName(VALID_LAST_NAME)
				.phoneNumber(VALID_PHONE_NUMBER)
				.address(VALID_ADDRESS)
				.birthDate(VALID_BIRTH_DATE)
				.build();
	}

	public static UserPatchRequestDto getMockValidUserPatchRequestDto() {
		return UserPatchRequestDto.builder()
				.firstName(UPDATED_FIRST_NAME)
				.address(VALID_ADDRESS)
				.build();
	}

	public static UserPatchRequestDto getMockInvalidUserPatchRequestDto() {
		return UserPatchRequestDto.builder()
				.phoneNumber(INVALID_PHONE_NUMBER)
				.address(VALID_ADDRESS)
				.build();
	}

	public static User getMockValidUser() {
		return User.builder()
				.id(VALID_USER_ID)
				.email(VALID_EMAIL)
				.firstName(VALID_FIRST_NAME)
				.lastName(VALID_LAST_NAME)
				.phoneNumber(VALID_PHONE_NUMBER)
				.address(VALID_ADDRESS)
				.birthDate(VALID_BIRTH_DATE)
				.build();
	}
}
