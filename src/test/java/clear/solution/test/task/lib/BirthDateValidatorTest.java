package clear.solution.test.task.lib;

import clear.solution.test.task.utils.SharedTestResources;
import jakarta.validation.ConstraintValidatorContext;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;


@ExtendWith(MockitoExtension.class)
public class BirthDateValidatorTest {
	@Mock
	private ConstraintValidatorContext constraintValidatorContext;

	@InjectMocks
	private BirthDateValidator birthDateValidator;

	@Test
	public void givenValidBirthDate_whenChecksIsValid_thenTrue() {
		LocalDate birthDate = SharedTestResources.VALID_BIRTH_DATE;

		Assertions.assertTrue(birthDateValidator.isValid(birthDate, constraintValidatorContext));
	}

	@Test
	public void givenInvalidBirthDate_whenChecksIsValid_thenFalse() {
		LocalDate birthDate = SharedTestResources.INVALID_BIRTH_DATE;

		Assertions.assertFalse(birthDateValidator.isValid(birthDate, constraintValidatorContext));
	}

	@Test
	public void givenFutureBirthDate_whenChecksIsValid_thenFalse() {
		LocalDate birthDate = LocalDate.now().plusDays(1);

		Assertions.assertFalse(birthDateValidator.isValid(birthDate, constraintValidatorContext));
	}

	@Test
	public void givenNullBirthDate_whenChecksIsValid_thenTrue() {
		Assertions.assertTrue(birthDateValidator.isValid(SharedTestResources.NULL_BIRTH_DATE,
				constraintValidatorContext));
	}
}
