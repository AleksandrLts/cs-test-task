package clear.solution.test.task.dto.request;

import clear.solution.test.task.lib.ValidBirthDate;
import clear.solution.test.task.lib.ValidEmail;
import clear.solution.test.task.lib.ValidPhoneNumber;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserRequestDto {
	@NotBlank(message = "Email can't be null or blank")
	@ValidEmail
	private String email;

	@NotBlank(message = "First name can't be null or blank")
	@Size(max = 50, message = "Size should be less than 50")
	private String firstName;

	@NotBlank(message = "Last name can't be null or blank")
	@Size(max = 50, message = "Size should be less than 50")
	private String lastName;

	@NotNull(message = "Birth date can't be null")
	@ValidBirthDate
	private LocalDate birthDate;

	private String address;

	@ValidPhoneNumber
	private String phoneNumber;
}
