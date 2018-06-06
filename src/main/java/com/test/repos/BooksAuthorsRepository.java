package com.test.repos;

import com.test.entyties.Books_has_Authors;
import org.springframework.data.repository.CrudRepository;

public interface BooksAuthorsRepository extends CrudRepository<Books_has_Authors, Long> {
}
