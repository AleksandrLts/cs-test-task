package clear.solution.test.task.lib;

import java.time.LocalDate;
import java.time.Period;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Value;

public class BirthDateValidator implements ConstraintValidator<ValidBirthDate, LocalDate> {
    @Value("${user.min.age}")
    private int validAge;

    @Override
    public boolean isValid(LocalDate birthDate, ConstraintValidatorContext constraintValidatorContext) {
        LocalDate currentDate = LocalDate.now();
        if (birthDate != null) {
            if (birthDate.isAfter(currentDate)) {
                return false;
            }
            int age = Period.between(birthDate, currentDate).getYears();
            return age >= validAge;
        }
        return true;
    }
}
