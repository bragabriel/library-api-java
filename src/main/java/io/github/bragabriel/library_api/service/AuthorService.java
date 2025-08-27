package io.github.bragabriel.library_api.service;

import io.github.bragabriel.library_api.model.Author;
import io.github.bragabriel.library_api.repository.AuthorRepository;
import io.github.bragabriel.library_api.specification.AuthorSpecification;
import org.apache.logging.log4j.util.Strings;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class AuthorService {

	private final AuthorRepository authorRepository;

	public AuthorService(AuthorRepository authorRepository) {
		this.authorRepository = authorRepository;
	}

	public Author save(Author author){
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
		authorRepository.delete(author);
	}

	public List<Author> find(String name, String nationality){
		return authorRepository.findAll(AuthorSpecification.findByNameAndNationality(name, nationality));

	}

}
