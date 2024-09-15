package com.bootlabs.demo.controller;


import com.bootlabs.demo.entities.Author;
import com.bootlabs.demo.service.AuthorService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 * REST controller for managing {@link Author}.
 *
 * @author @bootteam
 */
@Slf4j
@RestController
@RequestMapping("/api/author")
public class AuthorController {

    private final AuthorService entityService;

    public AuthorController(AuthorService entityService) {
        this.entityService = entityService;
    }

    /**
     * {@code POST  /author} : Create a new author.
     *
     * @param author the author to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new author.
     */
    @PostMapping()
    public ResponseEntity<Author> createAuthor(@RequestBody @Valid Author author) {
        log.debug("REST request to save Author : {}", author);
        return new ResponseEntity<>(entityService.create(author), HttpStatus.CREATED);
    }

    /**
     * {@code PUT  /author} : Updates an existing author.
     *
     * @param author the author to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated author,
     * or with status {@code 400 (Bad Request)} if the author is not valid,
     * or with status {@code 500 (Internal Server Error)} if the author couldn't be updated.
     */
    @PutMapping()
    public ResponseEntity<Author> updateAuthor(@Valid @RequestBody Author author) {
        log.debug("REST request to update Author : {}", author);
        Author result = entityService.update(author);
        return ResponseEntity.ok().body(result);
    }

    /**
     * {@code GET  /author} : get all the authors.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of author in body.
     */

    @GetMapping()
    public ResponseEntity<List<Author>> getAllAuthor() {
        log.debug("REST request to get all authors");
        List<Author> lst = entityService.getAll();

        return new ResponseEntity<>(lst, HttpStatus.OK);
    }

    /**
     * {@code GET  /author/:id} : get the "id" author.
     *
     * @param id the id of the author to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the author, or with status {@code 404 (Not Found)}.
     */
    @GetMapping(value = "/{id}")
    public ResponseEntity<Author> getOneAuthor(@PathVariable("id") Long id) {
        log.debug("REST request to get Author : {}", id);
        Author e = entityService.getOne(id);

        return new ResponseEntity<>(e, HttpStatus.OK);
    }

    /**
     * {@code DELETE  /author/:id} : delete the "id" author.
     *
     * @param id the id of the author to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAuthor(@PathVariable("id") Long id) {
        log.debug("REST request to delete Author : {}", id);
        entityService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
