package io.github.bragabriel.library_api.repository;

import io.github.bragabriel.library_api.model.Author;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

/**
 * @see AuthorRepositoryTest
 */
@Repository
public interface AuthorRepository extends JpaRepository<Author, UUID>, JpaSpecificationExecutor<Author> {
//	List<Author> findByName(String name);
//	List<Author> findByNationality(String nationality);
//	List<Author> findByNameAndNationality(String name, String nationality);
}
