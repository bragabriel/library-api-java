package io.github.bragabriel.library_api.service;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import static io.github.bragabriel.library_api.repository.specs.BookSpecs.genreEqual;
import static io.github.bragabriel.library_api.repository.specs.BookSpecs.isbnEqual;
import static io.github.bragabriel.library_api.repository.specs.BookSpecs.nameAuthorLike;
import static io.github.bragabriel.library_api.repository.specs.BookSpecs.publicationDateEqual;
import static io.github.bragabriel.library_api.repository.specs.BookSpecs.titleLike;

import io.github.bragabriel.library_api.dto.BookCreateDto;
import io.github.bragabriel.library_api.mapper.BookMapper;
import io.github.bragabriel.library_api.model.Book;
import io.github.bragabriel.library_api.model.BookGenreEnum;
import io.github.bragabriel.library_api.repository.BookRepository;
import io.github.bragabriel.library_api.validator.BookValidator;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BookService {

	private final BookRepository bookRepository;
	private final BookMapper bookMapper;
    private final BookValidator validator;

	public Book save(BookCreateDto dto){
		Book book = bookMapper.toEntity(dto);
        validator.validate(book);
		return bookRepository.save(book);
	}

	public Optional<Book> getById(UUID id) {
		return bookRepository.findById(id);
	}

    public void delete(Book book){
        bookRepository.delete(book);
    }

    public Page<Book> find(String isbn, String title, String nameAuthor, BookGenreEnum genre, Integer publicationDate,
                           Integer page, Integer pageSize){
        //Initializing specification (1 = 1)
        Specification<Book> specs = (root, query, cb) -> cb.conjunction();

        if(isbn != null){
            specs = specs.and(isbnEqual(isbn));
        }

        if(title != null){
            specs = specs.and(titleLike(title));
        }

        if(nameAuthor != null){
            specs = specs.and(nameAuthorLike(nameAuthor));
        }

        if(genre != null){
            specs = specs.and(genreEqual(genre));
        }

        if(publicationDate != null){
            specs = specs.and(publicationDateEqual(publicationDate));
        }

        Pageable pageable = PageRequest.of(page, pageSize);

        return bookRepository.findAll(specs, pageable);
    }

    public void update(Book book) {
        validator.validate(book);
        bookRepository.save(book);
    }
}
