package com.test.entyties;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "books_has_authors")
public class Books_has_Authors implements Serializable
{
    private Integer ID;
    private Integer BooksID;
    private Integer AuthorsID;

    public Books_has_Authors(){}

    public Books_has_Authors(Integer bookid, Integer authorid)
    {
        BooksID = bookid;
        AuthorsID = authorid;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id", nullable=false, length=45)
    public Integer getID() {
        return ID;
    }

    public void setID(Integer ID) {
        this.ID = ID;
    }

    @Basic
    @Column(name="books_id", nullable=false, length=11)
    public Integer getBooksID() {
        return BooksID;
    }

    public void setBooksID(Integer booksID) {
        BooksID = booksID;
    }

    @Basic
    @Column(name="authors_id", nullable=false, length=11)
    public Integer getAuthorsID() {
        return AuthorsID;
    }

    public void setAuthorsID(Integer authorsID) {
        AuthorsID = authorsID;
    }

    @Override
    public boolean equals(Object obj)
    {
        if (this == obj)
            return true;
        if (obj == null || getClass() != obj.getClass())
            return false;
        Books_has_Authors other = (Books_has_Authors) obj;
        try
        {
            return ((BooksID == null || other.BooksID == null) ||  Objects.equals(BooksID, other.BooksID))
                    && ((AuthorsID == null || other.AuthorsID == null) || Objects.equals(AuthorsID, other.AuthorsID));
        }
        catch (Exception e)
        {
            return false;
        }
    }
}
