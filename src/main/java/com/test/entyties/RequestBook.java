package com.test.entyties;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import java.util.ArrayList;
import java.util.List;

@JsonRootName(value = "book")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RequestBook extends Book
{
    @JsonProperty(value = "authors")
    private List<Author> authors;

    public RequestBook()
    {
        super();
    }

    public Book explicit()
    {
        Book book = new Book();
        book.ID = ID;
        book.year = year;
        book.title = title;
        return book;
    }
    private static Book explicit(Book book, Iterable<Author> authors)
    {
        RequestBook requestBook = new RequestBook();
        requestBook.title = book.title;
        requestBook.year = book.year;
        requestBook.ID = book.ID;
        if (authors != null)
        {
            requestBook.authors = new ArrayList<>();
            for (Books_has_Authors books_has_author : book.books_has_authors)
                for (Author author : authors)
                    if (author.getID().equals(books_has_author.getAuthorsID()))
                        requestBook.authors.add(author);
        }
        return requestBook;
    }

    public static List<RequestBook> explicit(Iterable<Book> books, Iterable<Author> authors)
    {
        List<RequestBook> books1 = new ArrayList<>();
        for (Book book : books)
            books1.add((RequestBook)explicit(book, authors));
        return books1;
    }

    public List<Author> getAuthors() {
        return authors;
    }

    public void setAuthors(List<Author> authors) {
        this.authors = authors;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder().append("Book :[ID:")
                .append(ID)
                .append(" Title:")
                .append(title)
                .append(" year:")
                .append(year)
                .append(" Author:[");
        for (Author author : authors)
            builder.append(author.toString());
        return builder.append("]").toString();
    }
}
