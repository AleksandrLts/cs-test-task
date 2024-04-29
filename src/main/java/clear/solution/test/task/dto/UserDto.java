package clear.solution.test.task.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {
	private Long id;

	private String email;

	private String firstName;

	private String lastName;

	private LocalDate birthDate;

	private String address;

	private String phoneNumber;
}
