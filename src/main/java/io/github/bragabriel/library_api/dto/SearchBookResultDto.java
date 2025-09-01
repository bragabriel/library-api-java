package io.github.bragabriel.library_api.dto;

import io.github.bragabriel.library_api.model.BookGenreEnum;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

public record SearchBookResultDto(
		UUID id, String isbn, String title, LocalDate publicationDate, BookGenreEnum genre, BigDecimal price,
		AuthorDto author
){
}
