package com.bootlabs.demo.repositories;

import com.bootlabs.demo.PostgresDataJpaTest;
import com.bootlabs.demo.entities.Author;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@PostgresDataJpaTest
@Sql({"/sql/init.sql"})
class AuthorRepositoryTest {

    @Autowired
    private AuthorRepository authorRepository;

    @Test
    void testEmbeddedDatabase() {
        Optional<Author> authorOptional = authorRepository.findById(1L);

        assertThat(authorOptional).hasValueSatisfying(author -> {
            assertThat(author.getId()).isNotNull();
            assertThat(author.getFirstname()).isEqualTo("Bree");
            assertThat(author.getLastname()).isEqualTo("Nasim");
        });
    }
}