package com.bootlabs.demo.service.impl;


import com.bootlabs.demo.entities.Book;
import com.bootlabs.demo.exception.BadRequestException;
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
    public Book update(Book d, Long id) {
        var book = repository.findById(id)
                .orElseThrow(() -> new BadRequestException("Id not found"));
        book.setPage(d.getPage());
        book.setTitle(d.getTitle());
        book.setDescription(d.getDescription());
        book.setIsbn(d.getIsbn());
        book.setPrice(d.getPrice());
        book.setAuthor(d.getAuthor());
        return repository.saveAndFlush(d);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Book getOne(Long id) {
        return repository.findById(id).orElseThrow(() -> new BadRequestException("Id not found"));
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
