package com.test.controllers;

import com.test.entyties.*;
import com.test.repos.AuthorsRepository;
import com.test.repos.BooksAuthorsRepository;
import com.test.repos.BooksRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.*;


@Controller
@RequestMapping(path="/test")
public class MainController
{
    @Autowired
    private BooksRepository booksRepository;
    @Autowired
    private AuthorsRepository authorsRepository;
    @Autowired
    private BooksAuthorsRepository booksAuthorsRepository;

    //Create
    @RequestMapping(value="/add", method = RequestMethod.POST, produces = "application/json", consumes = "application/json")
    @ResponseBody public StatusResponse addNewBook(@RequestBody RequestBook json)
    {
        if (json == null)
            return new StatusResponse(3, "Unexpected request");

        if (json.getTitle() == null)
            return new StatusResponse(4, "Illegal object");

        if (json.getAuthors() == null)
            return new StatusResponse(2, "No one author was find, no one saved");

        try
        {
            List<Integer> authorsIDs = new ArrayList<>(5);
            List<Author> tobase = new ArrayList<>(5);
            for (Author reqAuthor : json.getAuthors())
            {
                reqAuthor.cleanup();
                boolean exist = false;
                for (Author author : authorsRepository.findAll())
                    if (author.equals(reqAuthor))
                    {
                        authorsIDs.add(author.getID());
                        exist = true;
                        break;
                    }
                if (!exist)
                    tobase.add(reqAuthor);
            }
            for (Author author : authorsRepository.saveAll(tobase))
                authorsIDs.add(author.getID());

            Integer bookID = booksRepository.save(json.explicit()).getID();
            List<Books_has_Authors> books_has_authors = new ArrayList<>(0);
            for (Integer authorsID : authorsIDs)
                books_has_authors.add(new Books_has_Authors(bookID, authorsID));
            booksAuthorsRepository.saveAll(books_has_authors);
            return new StatusResponse(1, "Book and authors successfully saved");
        }
        catch (Exception exc)
        {
            exc.printStackTrace(System.out);
            return new StatusResponse(400, "Unexpected DB error");
        }
    }

    //Read
    @RequestMapping(value="/books", method = RequestMethod.GET)
    public @ResponseBody ResponseBooks getAllBooks(boolean authors)
    {
        ResponseBooks books = new ResponseBooks();
        books.setBooks(RequestBook.explicit(booksRepository.findAll(), authors ? authorsRepository.findAll() : null));
        return books;
    }

    //Update
    @RequestMapping(value="/update", method = RequestMethod.POST, produces = "application/json", consumes = "application/json")
    public @ResponseBody StatusResponse UpdateBook(@RequestBody RequestBook json)
    {
        if (json == null)
            return new StatusResponse(3, "Unexpected request");

        if (json.getID() == null || json.getAuthors() == null)
            return new StatusResponse(4, "Illegal request body");

        try
        {
        Iterable<Book> books = booksRepository.findAll();
        Book find;
        block:
        {
            for (Book book : books)
                if (book.getID().equals(json.getID()))
                {
                    find = book;
                    break block;
                }
            return new StatusResponse(5, "Can't find record in table");
        }

        Integer saved = booksRepository.save(find.merge(json.explicit())).getID();
        Iterable<Author> authors = authorsRepository.findAll();
        List<Integer> newAuthorIDs = new ArrayList<>(5);
        List<Integer> oldAuthorIDs = new ArrayList<>(5);
        Set<Books_has_Authors> old = find.getBooks_has_authors();
        Set<Books_has_Authors> young = new HashSet<>(5);
        Set<Books_has_Authors> trash = new HashSet<>(5);

        for (Author author : json.getAuthors())
        {
            if (author.getID() == null)
            {
                newAuthorIDs.add(authorsRepository.save(author).getID());
            }
            else
            {
                for (Author author1 : authors)
                    if (author1.getID().equals(author.getID()))
                    {
                        oldAuthorIDs.add(authorsRepository.save(author1.merge(author)).getID());
                        break;
                    }
            }
        }
        for (Books_has_Authors hasAuthors : old)
        {
            int i = 0;
            block:
            {
                for (Integer authorid : oldAuthorIDs)
                {
                    if (hasAuthors.getAuthorsID().equals(authorid))
                    {
                        break block;
                    }
                    i++;
                }
                if (i == old.size())
                {
                    trash.add(hasAuthors);
                }
            }
        }
        booksAuthorsRepository.deleteAll(trash);
        for(Integer authID : newAuthorIDs)
            young.add(new Books_has_Authors(saved, authID));
            booksAuthorsRepository.saveAll(young);
        }
        catch (Exception exc)
        {
            exc.printStackTrace(System.out);
            return new StatusResponse(400, "Unexpected DB error");
        }
        return new StatusResponse(1, "Book and authors successfully saved");
    }

    //Delete
    @GetMapping(path="/deletebook")
    public @ResponseBody StatusResponse getAllBooksAuthors(Integer id)
    {
        if (id == null || id <= 0)
            return new StatusResponse(4, "Illegal request body");
        try
        {
            for (Book book : booksRepository.findAll())
            {
                if (book.getID().equals(id))
                {
                    List<Books_has_Authors> trash = new ArrayList<>(5);
                    for (Books_has_Authors books_has_authors : booksAuthorsRepository.findAll())
                        if (books_has_authors.getBooksID().equals(id))
                            trash.add(books_has_authors);
                    booksAuthorsRepository.deleteAll(trash);
                    booksRepository.delete(book);
                    return new StatusResponse(1, "Successfully deleted");
                }
            }
        }
        catch (Exception exc)
        {
            exc.printStackTrace(System.out);
            return new StatusResponse(400, "Unexpected DB error");
        }

        return new StatusResponse(5, "Can't find record in table");
    }
}
