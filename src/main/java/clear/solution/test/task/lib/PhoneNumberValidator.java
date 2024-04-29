package clear.solution.test.task.lib;

import clear.solution.test.task.model.User;
import clear.solution.test.task.service.UserService;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class PhoneNumberValidator implements ConstraintValidator<ValidPhoneNumber, String> {
    private static final String PHONE_NUMBER_PATTERN = "\\+380\\d{9}";
    private final UserService userService;

    @Override
    public boolean isValid(String phoneNumber, ConstraintValidatorContext context) {
        if (phoneNumber == null) {
            return true;
        }
        User user = userService.findUserByPhoneNumber(phoneNumber);
        if (user != null) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("This phone number already exists")
                    .addConstraintViolation();
            return false;
        }
        return phoneNumber.matches(PHONE_NUMBER_PATTERN);
    }
}
