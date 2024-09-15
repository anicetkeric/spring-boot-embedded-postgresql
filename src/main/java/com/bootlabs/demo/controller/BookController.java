package com.bootlabs.demo.controller;


import com.bootlabs.demo.entities.Book;
import com.bootlabs.demo.service.BookService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 * REST controller for managing {@link Book}.
 *
 * @author @bootteam
 */
@Slf4j
@RestController
@RequestMapping("/api/book")
public class BookController {

    private final BookService entityService;

    public BookController(BookService entityService) {
        this.entityService = entityService;
    }

    /**
     * {@code POST  /book} : Create a new book.
     *
     * @param book the book to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new book.
     */
    @PostMapping()
    public ResponseEntity<Book> createBook(@RequestBody @Valid Book book) {
        log.debug("REST request to save Book : {}", book);
        return new ResponseEntity<>(entityService.create(book), HttpStatus.CREATED);
    }

    /**
     * {@code PUT  /book} : Updates an existing book.
     *
     * @param book the book to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated book,
     * or with status {@code 400 (Bad Request)} if the book is not valid,
     * or with status {@code 500 (Internal Server Error)} if the book couldn't be updated.
     */
    @PutMapping()
    public ResponseEntity<Book> updateBook(@Valid @RequestBody Book book) {
        log.debug("REST request to update Book : {}", book);
        Book result = entityService.update(book);
        return ResponseEntity.ok().body(result);
    }

    /**
     * {@code GET  /book} : get all the books.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of book in body.
     */

    @GetMapping()
    public ResponseEntity<List<Book>> getAllBook() {
        log.debug("REST request to get all books");
        List<Book> lst = entityService.getAll();

        return new ResponseEntity<>(lst, HttpStatus.OK);
    }

    /**
     * {@code GET  /book/:id} : get the "id" book.
     *
     * @param id the id of the book to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the book, or with status {@code 404 (Not Found)}.
     */
    @GetMapping(value = "/{id}")
    public ResponseEntity<Book> getOneBook(@PathVariable("id") Long id) {
        log.debug("REST request to get Book : {}", id);
        Book e = entityService.getOne(id);

        return new ResponseEntity<>(e, HttpStatus.OK);
    }

    /**
     * {@code DELETE  /book/:id} : delete the "id" book.
     *
     * @param id the id of the book to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBook(@PathVariable("id") Long id) {
        log.debug("REST request to delete Book : {}", id);
        entityService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}