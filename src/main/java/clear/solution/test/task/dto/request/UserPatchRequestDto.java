package clear.solution.test.task.dto.request;

import clear.solution.test.task.lib.ValidBirthDate;
import clear.solution.test.task.lib.ValidEmail;
import clear.solution.test.task.lib.ValidPhoneNumber;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserPatchRequestDto {
	@ValidEmail
	private String email;

	@Pattern(regexp = "\\S.*", message = "Should be not empty")
	private String firstName;

	@Pattern(regexp = "\\S.*", message = "Should be not empty")
	private String lastName;

	@ValidBirthDate
	private LocalDate birthDate;

	private String address;

	@ValidPhoneNumber
	private String phoneNumber;
}
