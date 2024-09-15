package com.bootlabs.demo.controller;

import com.bootlabs.demo.AbstractIntegrationTest;
import com.bootlabs.demo.IntegrationTest;
import com.bootlabs.demo.entities.Author;
import com.bootlabs.demo.repositories.AuthorRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.text.MessageFormat;
import java.util.List;
import java.util.function.Consumer;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;



/**
 * Integration tests for the {@link AuthorController} REST controller.
 */
class AuthorControllerIT extends AbstractIntegrationTest {

    @Autowired
    private AuthorRepository repository;

    private Author author;

    @BeforeEach
    public void initTest() {
        repository.deleteAll();
        author = Author.builder()
                .firstname("Jhon")
                .lastname("Doe")
                .build();
    }

    @Test
    @Transactional
    void createAuthor() throws Exception {
        int databaseSizeBeforeCreate = repository.findAll().size();

        mockMvc
                .perform(post(MessageFormat.format("{0}/author", BASE_CONTROLLER_ENDPOINT)).contentType(MediaType.APPLICATION_JSON)
                        .content(TestUtil.convertObjectToJsonBytes(author)))
                .andExpect(status().isCreated());

        // Validate the Author in the database
        assertPersistedAuthors(authors -> {
            assertThat(authors).hasSize(databaseSizeBeforeCreate + 1);
            Author testAuthor = authors.getLast();
            assertThat(testAuthor.getLastname()).isEqualTo(author.getLastname());
            assertThat(testAuthor.getFirstname()).isEqualTo(author.getFirstname());
        });
    }
    
    /*
    @Test
    @Transactional
    void createAuthorTest() throws Exception {
        int databaseSizeBeforeCreate = repository.findAll().size();

        // Create the Author
        mockMvc.perform(post("/api/projets")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(TestUtil.convertObjectToJsonBytes(author)))
                .andExpect(status().isCreated());

        // Validate the Author in the database
        List<Author> projetList = repository.findAll();
        assertThat(projetList).hasSize(databaseSizeBeforeCreate + 1);
        Author testAuthor = projetList.get(projetList.size() - 1);
        assertThat(testAuthor.getFirstname()).isEqualTo(DEFAULT_CODE);
        assertThat(testAuthor.getDateDebut()).isEqualTo(DEFAULT_DATE_DEBUT);
        assertThat(testAuthor.getDateFin()).isEqualTo(DEFAULT_DATE_FIN);
    }
*/
/*    @Test
    @Transactional
    void createAuthorWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = repository.findAll().size();

        // Create the Author with an existing ID
        projet.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        mockMvc.perform(post("/api/projets").with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(TestUtil.convertObjectToJsonBytes(projet)))
                .andExpect(status().isBadRequest());

        // Validate the Author in the database
        List<Author> projetList = repository.findAll();
        assertThat(projetList).hasSize(databaseSizeBeforeCreate);
    }*/


    private void assertPersistedAuthors(Consumer<List<Author>> userAssertion) {
        userAssertion.accept(repository.findAll());
    }
}