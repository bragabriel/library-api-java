package io.github.bragabriel.library_api.service;

import io.github.bragabriel.library_api.dto.BookCreateDto;
import io.github.bragabriel.library_api.mapper.BookMapper;
import io.github.bragabriel.library_api.model.Book;
import io.github.bragabriel.library_api.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class BookService {

	private final BookRepository bookRepository;
	private final BookMapper bookMapper;

	public Book save(BookCreateDto dto){
		Book book = bookMapper.toEntity(dto);
		return bookRepository.save(book);
	}

	public Optional<Book> getById(UUID id) {
		return bookRepository.findById(id);
	}

    public void delete(Book book){
        bookRepository.delete(book);
    }
}
