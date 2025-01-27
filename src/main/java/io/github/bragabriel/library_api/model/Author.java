package io.github.bragabriel.library_api.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name="author", schema = "public")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Author {

    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(length = 100, nullable = false)
    private String name;

    @Column(nullable = false)
    private LocalDate birthdate;

    @Column(length = 50, nullable = false)
    private String nationality;

    @OneToMany(mappedBy = "author")
    private List<Book> bookList;
}
