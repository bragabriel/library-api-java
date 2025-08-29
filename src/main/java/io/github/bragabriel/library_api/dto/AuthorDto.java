package io.github.bragabriel.library_api.dto;

import io.github.bragabriel.library_api.model.Author;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;
import java.util.UUID;

public record AuthorDto(
		UUID id,
		@NotBlank(message = "This is a required field")
		@Size(max = 100, message = "Must not exceed 100 characters")
		String name,
		@NotNull(message = "This is a required field")
		@Past(message = "Must be a past date")
		LocalDate birthdate,
		@NotBlank(message = "This is a required field")
		@Size(max = 50, message = "Must not exceed 50 characters")
		String nationality
) {

	public Author mapToAuthorEntity(){
		return Author.builder()
				.name(name)
				.birthdate(birthdate)
				.nationality(nationality)
				.build();
	}
}
