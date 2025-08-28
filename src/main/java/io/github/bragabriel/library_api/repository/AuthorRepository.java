package io.github.bragabriel.library_api.repository;

import io.github.bragabriel.library_api.model.Author;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * @see AuthorRepositoryTest
 */
@Repository
public interface AuthorRepository extends JpaRepository<Author, UUID>, JpaSpecificationExecutor<Author> {

	Optional<Author> findByNameAndBirthdateAndNationality(String name, LocalDate birthDate, String nationality);
}
