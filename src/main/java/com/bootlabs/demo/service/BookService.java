package com.bootlabs.demo.service;

import com.bootlabs.demo.entities.Book;

import java.util.List;

/**
 * Service Interface for managing {@link Book}.
*  @author @bootteam
 */
public interface BookService {
   
    /**
     * create new item for domain
     *
     * @param entity entity to save.
     * @return persisted entity Book
     */
    Book create(Book entity);

    /**
     * Update entity. Check before if existing data. If data is empty throw Exception
     *
     * @param entity domain
     * @param id the id of the entity.
     * @return Book
     */
    Book update(Book entity, Long id);

    /**
     * get Book by id. Can be return empty
     *
     * @param id the id of the entity.
     * @return Book
     */
    Book getOne(Long id) ;

    /**
     * Get all entities
     *
     * @return list of entities List<Book>
     */
    List<Book> getAll();

    /**
     * Delete record by id
     *  
     */
    void delete(Long id);
}
