package io.github.bragabriel.library_api.service;

import io.github.bragabriel.library_api.dto.BookCreateDto;
import io.github.bragabriel.library_api.mapper.BookMapper;
import io.github.bragabriel.library_api.model.Book;
import io.github.bragabriel.library_api.model.BookGenreEnum;
import io.github.bragabriel.library_api.repository.BookRepository;
import io.github.bragabriel.library_api.repository.specs.BookSpecs;
import lombok.RequiredArgsConstructor;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
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

    public List<Book> find(String isbn, String title, String nameAuthor, BookGenreEnum genre, Integer publicationDate){
        //Initializing specification (1 = 1)
        Specification<Book> specs = Specification
                .where((root, query, cb) -> cb.conjunction());

        if(isbn != null){
            specs = specs.and(BookSpecs.isbnEqual(isbn));
        }

        if(title != null){
            specs = specs.and(BookSpecs.titleLike(title));
        }

        if(nameAuthor != null){
            specs = specs.and(BookSpecs.nameAuthorLike(nameAuthor));
        }

        if(genre != null){
            specs = specs.and(BookSpecs.genreEqual(genre));
        }

        if(publicationDate != null){
            specs = specs.and(BookSpecs.publicationDateEqual(publicationDate));
        }

        return bookRepository.findAll();
    }

}
