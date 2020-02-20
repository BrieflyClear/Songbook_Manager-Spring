package com.lazydev.stksongbook.webapp.dao;

import com.lazydev.stksongbook.webapp.model.Author;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthorDAO extends CrudRepository<Author, Long> {
}
