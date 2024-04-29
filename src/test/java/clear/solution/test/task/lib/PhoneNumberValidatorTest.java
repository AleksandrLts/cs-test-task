package clear.solution.test.task.lib;

import clear.solution.test.task.model.User;
import clear.solution.test.task.service.UserService;
import clear.solution.test.task.utils.SharedTestResources;
import jakarta.validation.ConstraintValidatorContext;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class PhoneNumberValidatorTest {

	@Mock
	private UserService userService;

	@Mock
	private ConstraintValidatorContext constraintValidatorContext;

	@InjectMocks
	private PhoneNumberValidator phoneNumberValidator;

	@Test
	public void givenValidPhoneNumber_whenChecksIsValid_thenTrue() {
		String phoneNumber = SharedTestResources.VALID_PHONE_NUMBER;

		when(userService.findUserByPhoneNumber(phoneNumber)).thenReturn(null);

		Assertions.assertTrue(phoneNumberValidator.isValid(phoneNumber, constraintValidatorContext));
	}

	@Test
	public void givenInvalidPhoneNumberFormat_whenChecksIsValid_thenFalse() {
		String phoneNumber = SharedTestResources.INVALID_PHONE_NUMBER;

		Assertions.assertFalse(phoneNumberValidator.isValid(phoneNumber, constraintValidatorContext));
	}

	@Test
	public void givenNullPhoneNumber_whenChecksIsValid_thenTrue() {
		Assertions.assertTrue(phoneNumberValidator.isValid(SharedTestResources.NULL_PHONE_NUMBER,
				constraintValidatorContext));
	}

	@Test
	public void givenPhoneNumberAlreadyExists_whenChecksIsValidWithMessageChange_thenFalse() {
		String phoneNumber = SharedTestResources.VALID_PHONE_NUMBER;
		User existingUser = new User();

		when(userService.findUserByPhoneNumber(phoneNumber)).thenReturn(existingUser);

		ConstraintValidatorContext.ConstraintViolationBuilder builder =
				mock(ConstraintValidatorContext.ConstraintViolationBuilder.class);
		when(constraintValidatorContext.buildConstraintViolationWithTemplate(anyString())).thenReturn(builder);

		constraintValidatorContext.disableDefaultConstraintViolation();

		Assertions.assertFalse(phoneNumberValidator.isValid(phoneNumber, constraintValidatorContext));

		verify(constraintValidatorContext).buildConstraintViolationWithTemplate("This phone number already exists");
	}
}
