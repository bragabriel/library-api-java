package io.github.bragabriel.library_api.handler;

import io.github.bragabriel.library_api.dto.ErrorField;
import io.github.bragabriel.library_api.dto.ErrorResponse;
import io.github.bragabriel.library_api.exceptions.DuplicatedRegisterException;
import io.github.bragabriel.library_api.exceptions.InvalidFieldException;
import io.github.bragabriel.library_api.exceptions.NotAllowedException;
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

	@ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ErrorResponse handleMethodArgumentNotValidException(MethodArgumentNotValidException e){
		List<FieldError> fieldErrors = e.getFieldErrors();
		List<ErrorField> errorsList = fieldErrors
				.stream()
				.map(fe -> new ErrorField(fe.getField(), fe.getDefaultMessage()))
				.collect(Collectors.toList());

		return new ErrorResponse(HttpStatus.UNPROCESSABLE_ENTITY.value(), "Validation error", errorsList);
	}

	@ResponseStatus(HttpStatus.CONFLICT)
	@ExceptionHandler(DuplicatedRegisterException.class)
	public ErrorResponse handleDuplicatedRegisterException(DuplicatedRegisterException e){
		return ErrorResponse.conflict(e.getMessage());
	}

    @ExceptionHandler(InvalidFieldException.class)
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    public ErrorResponse handleFieldException(InvalidFieldException e){
        return new ErrorResponse(
                HttpStatus.UNPROCESSABLE_ENTITY.value(),
                "Validation error",
                List.of(new ErrorField(e.getField(), e.getMessage()))
        );
    }

	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(NotAllowedException.class)
	public ErrorResponse handleNotAllowedException(NotAllowedException e){
		return ErrorResponse.defaultResponse(e.getMessage());
	}

	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	@ExceptionHandler(RuntimeException.class)
	public ErrorResponse handleDefaultError(RuntimeException e){
		return new ErrorResponse(
				HttpStatus.INTERNAL_SERVER_ERROR.value(),
				"An unexpected error occurred. Please contact your administrator.",
				List.of()
		);
	}

}
