package io.github.bragabriel.library_api.handler;

import io.github.bragabriel.library_api.dto.ErrorField;
import io.github.bragabriel.library_api.dto.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(MethodArgumentNotValidException.class)
	@ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
	public ErrorResponse handleMethodArgumentNotValidException(MethodArgumentNotValidException e){
		List<FieldError> fieldErrors = e.getFieldErrors();
		List<ErrorField> errorsList = fieldErrors
				.stream()
				.map(fe -> new ErrorField(fe.getField(), fe.getDefaultMessage()))
				.collect(Collectors.toList());

		return new ErrorResponse(HttpStatus.UNPROCESSABLE_ENTITY.value(), "Validation error", errorsList);
	}
}
