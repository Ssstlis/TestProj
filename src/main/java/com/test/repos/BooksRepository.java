package com.test.repos;

import com.test.entyties.Book;
import org.springframework.data.repository.CrudRepository;

public interface BooksRepository extends CrudRepository<Book, Long> {
}
