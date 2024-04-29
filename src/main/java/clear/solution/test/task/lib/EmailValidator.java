package clear.solution.test.task.lib;

import clear.solution.test.task.model.User;
import clear.solution.test.task.service.UserService;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class EmailValidator implements ConstraintValidator<ValidEmail, String> {
	private static final String EMAIL_PATTERN = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
	private final UserService userService;

	@Override
	public boolean isValid(String email, ConstraintValidatorContext context) {
		if (email != null) {
			User user = userService.findUserByEmail(email);
			if (user != null) {
				context.disableDefaultConstraintViolation();
				context.buildConstraintViolationWithTemplate("This email already exists")
						.addConstraintViolation();
				return false;
			}
			return email.matches(EMAIL_PATTERN);
		}
		return true;
	}
}
