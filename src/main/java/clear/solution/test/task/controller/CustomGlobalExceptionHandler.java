package clear.solution.test.task.controller;

import clear.solution.test.task.dto.ResponseErrorDto;
import clear.solution.test.task.exception.DateRangeException;
import clear.solution.test.task.exception.UserNotFoundException;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;
import java.util.List;

@RestControllerAdvice
public class CustomGlobalExceptionHandler extends ResponseEntityExceptionHandler {

	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
																  HttpHeaders headers,
																  HttpStatusCode status,
																  WebRequest request) {
		List<String> errorList = ex.getBindingResult().getFieldErrors().stream()
				.map(DefaultMessageSourceResolvable::getDefaultMessage).toList();

		ResponseErrorDto errorDto = ResponseErrorDto.builder()
				.time(LocalDateTime.now())
				.statusCode(HttpStatus.BAD_REQUEST.name())
				.errorMessage(errorList)
				.build();

		return ResponseEntity.status(HttpStatus.valueOf(errorDto.getStatusCode())).body(errorDto);
	}

	@ExceptionHandler(value = {UserNotFoundException.class})
	protected ResponseEntity<ResponseErrorDto> handleNotFound(UserNotFoundException ex) {
		return createResponseEntity(HttpStatus.NOT_FOUND, ex);
	}

	@ExceptionHandler(value = {DateRangeException.class})
	protected ResponseEntity<ResponseErrorDto> handleNotFound(DateRangeException ex) {
		return createResponseEntity(HttpStatus.BAD_REQUEST, ex);
	}

	@ExceptionHandler(value = {NullPointerException.class})
	protected ResponseEntity<ResponseErrorDto> handleNullPointerException(NullPointerException ex) {
		return createResponseEntity(HttpStatus.NOT_FOUND, ex);
	}

	private ResponseEntity<ResponseErrorDto> createResponseEntity(HttpStatus status, Exception ex) {
		ResponseErrorDto errorDto = ResponseErrorDto.builder()
				.time(LocalDateTime.now())
				.statusCode(status.name())
				.errorMessage(List.of(ex.getMessage()))
				.build();
		return ResponseEntity.status(HttpStatus.valueOf(errorDto.getStatusCode()))
				.body(errorDto);
	}
}


