package io.github.bragabriel.library_api.controller;

import io.github.bragabriel.library_api.dto.BookCreateDto;
import io.github.bragabriel.library_api.model.Book;
import io.github.bragabriel.library_api.service.BookService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@RestController
@RequiredArgsConstructor
@RequestMapping("books")
public class BookController implements GenericController {

	private final BookService bookService;

	@PostMapping
	public ResponseEntity<Void> save(@RequestBody @Valid BookCreateDto dto) {
		Book savedBook = bookService.save(dto);
		URI url = generateHeaderLocation(savedBook.getId());
		return ResponseEntity.created(url).build();
	}
}
