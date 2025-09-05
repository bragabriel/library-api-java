package io.github.bragabriel.library_api.controller;

import io.github.bragabriel.library_api.dto.BookCreateDto;
import io.github.bragabriel.library_api.dto.SearchBookResultDto;
import io.github.bragabriel.library_api.mapper.BookMapper;
import io.github.bragabriel.library_api.model.Book;
import io.github.bragabriel.library_api.service.BookService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("books")
public class BookController implements GenericController {

	private final BookService bookService;
	private final BookMapper mapper;

	@PostMapping
	public ResponseEntity<Void> save(@RequestBody @Valid BookCreateDto dto) {
		Book savedBook = bookService.save(dto);
		URI url = generateHeaderLocation(savedBook.getId());
		return ResponseEntity.created(url).build();
	}

	@GetMapping("{id}")
	public ResponseEntity<SearchBookResultDto> getDetails(@PathVariable("id") String id){
		return bookService.getById(UUID.fromString(id))
				.map(book -> {
					var dto = mapper.toDto(book);
					return ResponseEntity.ok(dto);
				}).orElseGet(() -> ResponseEntity.notFound().build());
	}

}
