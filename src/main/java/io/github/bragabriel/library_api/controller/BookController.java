package io.github.bragabriel.library_api.controller;

import java.net.URI;
import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.github.bragabriel.library_api.dto.BookCreateDto;
import io.github.bragabriel.library_api.dto.SearchBookResultDto;
import io.github.bragabriel.library_api.mapper.BookMapper;
import io.github.bragabriel.library_api.model.Book;
import io.github.bragabriel.library_api.service.BookService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

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

    @DeleteMapping("{id}")
    public ResponseEntity<Object> delete(@PathVariable("id") String id){
        return bookService.getById(UUID.fromString(id))
                .map(book -> {
                            bookService.delete(book);
                            return ResponseEntity.noContent().build();
                }).orElseGet(() -> ResponseEntity.notFound().build());
    }

}
