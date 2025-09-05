package io.github.bragabriel.library_api.mapper;

import io.github.bragabriel.library_api.dto.BookCreateDto;
import io.github.bragabriel.library_api.dto.SearchBookResultDto;
import io.github.bragabriel.library_api.model.Book;
import io.github.bragabriel.library_api.repository.AuthorRepository;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(componentModel = "spring", uses = AuthorMapper.class)
public abstract class BookMapper {

	@Autowired
	protected AuthorRepository authorRepository;

	@Mapping(target = "author", expression = "java( authorRepository.findById(dto.idAuthor()).orElse(null) )")
	public abstract Book toEntity(BookCreateDto dto);

	public abstract SearchBookResultDto toDto(Book book);
}
