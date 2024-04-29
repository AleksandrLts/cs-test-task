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

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class EmailValidatorTest {
	@Mock
	private UserService userService;

	@Mock
	private ConstraintValidatorContext constraintValidatorContext;

	@InjectMocks
	private EmailValidator emailValidator;

	@Test
	public void givenValidEmail_whenChecksIsValid_thenTrue() {
		String email = SharedTestResources.VALID_EMAIL;
		when(userService.findUserByEmail(email)).thenReturn(null);
		Assertions.assertTrue(emailValidator.isValid(email, constraintValidatorContext));
	}

	@Test
	public void givenInvalidEmail_whenChecksIsValid_thenFalse() {
		String email = SharedTestResources.INVALID_EMAIL;
		Assertions.assertFalse(emailValidator.isValid(email, constraintValidatorContext));
	}

	@Test
	public void givenEmailAlreadyExists_whenChecksIsValidWithMessageChange_thenFalse() {
		String email = SharedTestResources.VALID_EMAIL;
		User existingUser = new User();
		when(userService.findUserByEmail(email)).thenReturn(existingUser);

		ConstraintValidatorContext.ConstraintViolationBuilder builder =
				mock(ConstraintValidatorContext.ConstraintViolationBuilder.class);
		when(constraintValidatorContext.buildConstraintViolationWithTemplate(anyString())).thenReturn(builder);

		constraintValidatorContext.disableDefaultConstraintViolation();

		Assertions.assertFalse(emailValidator.isValid(email, constraintValidatorContext));

		verify(constraintValidatorContext).buildConstraintViolationWithTemplate("This email already exists");
	}


	@Test
	public void givenNullEmail_whenChecksIsValid_thenTrue() {
		Assertions.assertTrue(emailValidator.isValid(SharedTestResources.NULL_EMAIL, constraintValidatorContext));
	}
}
