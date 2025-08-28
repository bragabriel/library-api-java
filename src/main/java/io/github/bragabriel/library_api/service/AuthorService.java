package io.github.bragabriel.library_api.service;

import io.github.bragabriel.library_api.exceptions.NotAllowedException;
import io.github.bragabriel.library_api.model.Author;
import io.github.bragabriel.library_api.repository.AuthorRepository;
import io.github.bragabriel.library_api.repository.BookRepository;
import io.github.bragabriel.library_api.specification.AuthorSpecification;
import io.github.bragabriel.library_api.validator.AuthorValidator;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.util.Strings;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AuthorService {

	private final AuthorRepository authorRepository;
	private final AuthorValidator validator;
	private final BookRepository bookRepository;

	public Author save(Author author){
		validator.validate(author);
		return authorRepository.save(author);
	}

	public void update(Author author){
		if(author.getId() == null){
			throw new IllegalArgumentException("Author must have an id");
		}
		authorRepository.save(author);
	}

	public Optional<Author> getById(UUID id){
		return authorRepository.findById(id);
	}

	public void delete(Author author){
		if(hasBook(author)){
			throw new NotAllowedException("You cannot delete an author who has books");
		}
		authorRepository.delete(author);
	}

	public List<Author> find(String name, String nationality){
		return authorRepository.findAll(AuthorSpecification.findByNameAndNationality(name, nationality));
	}

	public boolean hasBook(Author author){
		return bookRepository.existsByAuthor(author);
	}

}
