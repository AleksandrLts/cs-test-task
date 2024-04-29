package clear.solution.test.task.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ResponseErrorDto {
	@NotNull
	LocalDateTime time;

	@NotNull
	String statusCode;

	List<String> errorMessage;

	List<String> stackTrace;
}
