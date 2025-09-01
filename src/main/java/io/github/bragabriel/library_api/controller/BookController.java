package io.github.bragabriel.library_api.controller;

import io.github.bragabriel.library_api.dto.BookCreateDto;
import io.github.bragabriel.library_api.dto.ErrorResponse;
import io.github.bragabriel.library_api.exceptions.DuplicatedRegisterException;
import io.github.bragabriel.library_api.service.BookService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("books")
public class BookController {

	private final BookService bookService;

	@PostMapping
	public ResponseEntity<Object> save(@RequestBody @Valid BookCreateDto dto){
		try{
			//var savedBook = bookService.save(dto);
			return ResponseEntity.status(201).body(dto);
		}catch (DuplicatedRegisterException e){
			var error = ErrorResponse.conflict(e.getMessage());
			return ResponseEntity.status(error.status()).body(error);
		}
	}
}
