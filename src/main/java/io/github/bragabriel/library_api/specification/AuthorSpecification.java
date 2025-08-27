package io.github.bragabriel.library_api.specification;

import io.github.bragabriel.library_api.model.Author;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class AuthorSpecification {

	public static Specification<Author> findByNameAndNationality(String name, String nationality) {
		return (root, query, cb) -> {
			List<Predicate> predicates = new ArrayList<>();

			if (name != null) {
				predicates.add(cb.equal(root.get("name"), name));
			}

			if (nationality != null) {
				predicates.add(cb.equal(root.get("nationality"), nationality));
			}

			return cb.and(predicates.toArray(new Predicate[0]));
		};
	}
}
