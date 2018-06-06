package com.test.repos;

import com.test.entyties.Author;
import org.springframework.data.repository.CrudRepository;

public interface AuthorsRepository extends CrudRepository<Author, Long> {
}
