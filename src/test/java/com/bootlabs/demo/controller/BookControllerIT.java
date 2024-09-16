package com.bootlabs.demo.controller;

import com.bootlabs.demo.AbstractIntegrationTest;
import com.bootlabs.demo.entities.Book;
import com.bootlabs.demo.repositories.BookRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.text.MessageFormat;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Integration tests for the {@link BookController} REST controller.
 */
class BookControllerIT extends AbstractIntegrationTest {

    @Autowired
    private BookRepository repository;

    private Book book;

    @BeforeEach
    public void initTest() {
        repository.deleteAll();
        book = Book.builder()
                .title("testing")
                .isbn("20351LOPf")
                .page(110)
                .price(214)
                .description("controller IT")
                .build();
    }

    @Test
    void createBook() throws Exception {
        int databaseSizeBeforeCreate = repository.findAll().size();

        mockMvc
                .perform(post(MessageFormat.format("{0}/book", BASE_CONTROLLER_ENDPOINT)).contentType(MediaType.APPLICATION_JSON)
                        .content(TestUtil.convertObjectToJsonBytes(book)))
                .andExpect(status().isCreated());


        var books = repository.findAll();
        assertThat(books).hasSize(databaseSizeBeforeCreate + 1);
        Optional<Book> bookOptional = books.stream().filter(b -> b.getIsbn().equals("20351LOPf")).findFirst();
        assertThat(bookOptional).hasValueSatisfying(testBook -> {
            assertThat(testBook.getId()).isNotNull();
            assertThat(testBook.getDescription()).isEqualTo("controller IT");
            assertThat(testBook.getIsbn()).isEqualTo("20351LOPf");
            assertThat(testBook.getPage()).isEqualTo(110);
            assertThat(testBook.getPrice()).isEqualTo(214);
            assertThat(testBook.getTitle()).isEqualTo("testing");
        });
    }

    @Test
    void updateBook() throws Exception {
        repository.save(book);
        int databaseSizeBeforeUpdate = repository.findAll().size();

        book.setTitle("update title");
        book.setIsbn("00125689");
        book.setPage(50);
        mockMvc
                .perform(put(MessageFormat.format("{0}/book/1", BASE_CONTROLLER_ENDPOINT)).contentType(MediaType.APPLICATION_JSON)
                        .content(TestUtil.convertObjectToJsonBytes(book)))
                .andExpect(status().isOk());


        var books = repository.findAll();
        assertThat(books).hasSize(databaseSizeBeforeUpdate);
        Optional<Book> bookOptional = books.stream().filter(b -> b.getIsbn().equals("00125689")).findFirst();
        assertThat(bookOptional).hasValueSatisfying(testBook -> {
            assertThat(testBook.getId()).isNotNull();
            assertThat(testBook.getDescription()).isEqualTo("controller IT");
            assertThat(testBook.getIsbn()).isEqualTo("00125689");
            assertThat(testBook.getPage()).isEqualTo(50);
            assertThat(testBook.getPrice()).isEqualTo(214);
            assertThat(testBook.getTitle()).isEqualTo("update title");
        });
    }

    @Test
    void getAllBook() throws Exception {
        repository.save(book);

        mockMvc.perform(get(MessageFormat.format("{0}/book", BASE_CONTROLLER_ENDPOINT))
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.[*]").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.[*].title").isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$.[*].isbn").value(book.getIsbn()));
    }

    @Test
    void getOneBook() throws Exception {
        repository.save(book);


        var books = repository.findAll();

        mockMvc.perform(get(MessageFormat.format("{0}/book/1", BASE_CONTROLLER_ENDPOINT))
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$.isbn").value(book.getIsbn()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.description").value(book.getDescription()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.page").value(book.getPage()));
    }

    @Test
    void getOneBookNotFound() throws Exception {
        mockMvc.perform(get(MessageFormat.format("{0}/book/1", BASE_CONTROLLER_ENDPOINT))
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }
    @Test
    void deleteBook() throws Exception {
        repository.save(book);

        mockMvc.perform(delete(MessageFormat.format("{0}/book/1", BASE_CONTROLLER_ENDPOINT))
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNoContent());
    }

}