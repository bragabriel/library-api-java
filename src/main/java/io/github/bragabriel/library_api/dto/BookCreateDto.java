package io.github.bragabriel.library_api.dto;

import io.github.bragabriel.library_api.model.BookGenreEnum;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import org.hibernate.validator.constraints.ISBN;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

public record BookCreateDto(
		@ISBN
		@NotBlank(message = "This is a required field")
		String isbn,

		@NotBlank(message = "This is a required field")
		String title,

		@NotNull(message = "This is a required field")
		@Past(message = "Must be a past date")
		LocalDate publicationDate,

		BookGenreEnum genre,

		BigDecimal price,

		@NotNull(message = "This is a required field")
		UUID idAuthor
){
}
