package com.bootlabs.demo.service.impl;


import com.bootlabs.demo.entities.Book;
import com.bootlabs.demo.repositories.BookRepository;
import com.bootlabs.demo.service.BookService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Service Implementation for managing {@link Book}.
 *
 * @author @bootteam
 */
@Service
@Transactional
public class BookServiceImpl implements BookService {

    private final BookRepository repository;

    public BookServiceImpl(BookRepository repo) {
        this.repository = repo;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Book create(Book d) {
        return repository.save(d);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Book update(Book d) {
        return repository.saveAndFlush(d);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Book getOne(Long id) {
        return repository.findById(id).orElse(null);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Book> getAll() {
        return repository.findAll();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void delete(Long id) {
        repository.deleteById(id);
    }
}
