package io.github.bragabriel.library_api.service;

import io.github.bragabriel.library_api.dto.AuthorDto;
import io.github.bragabriel.library_api.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BookService {

	private final BookRepository bookRepository;

	public void save(AuthorDto dto){

	}
}
