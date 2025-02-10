package io.github.bragabriel.library_api.repository;

import io.github.bragabriel.library_api.model.Author;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

/**
 * @see AuthorRepositoryTest
 */
@Repository
public interface AuthorRepository extends JpaRepository<Author, UUID> {
}
