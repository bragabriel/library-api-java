package io.github.bragabriel.library_api.dto;

import io.github.bragabriel.library_api.model.Author;

import java.time.LocalDate;
import java.util.UUID;

public record AuthorDto(
		UUID id,
		String name,
		LocalDate birthdate,
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
