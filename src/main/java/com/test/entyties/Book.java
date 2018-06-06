package com.test.entyties;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import javax.persistence.*;
import java.io.Serializable;
import java.util.*;

@Entity
@Table(name = "books")
@JsonRootName(value = "book")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Book implements Serializable
{
    @JsonProperty(value = "id")
    protected Integer ID;
    @JsonProperty(value = "year")
    protected Integer year;
    @JsonProperty(value = "title")
    protected String title;
    @JsonIgnore
    protected Set<Books_has_Authors> books_has_authors = new HashSet<>();

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="book_id", nullable=false, length=45)
    public Integer getID() {
        return ID;
    }

    public void setID(Integer ID) {
        this.ID = ID;
    }

    @Basic
    @Column(name="year", nullable=true, length=45)
    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    @Basic
    @Column(name="title", nullable=false, length=45)
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

   @Override
    public String toString() {
        StringBuilder builder = new StringBuilder().append("Book :[ID:")
                .append(ID)
                .append(" Title:")
                .append(title)
                .append(" year:")
                .append(year);

        return builder.append("]").toString();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null || getClass() != obj.getClass())
            return false;
        Book other = (Book) obj;
        try
        {
            return ((title == null || other.title == null) ||  title.equalsIgnoreCase(other.title))
                    && ((year == null || other.year == null) || Objects.equals(year, other.year));
        }
        catch (Exception e)
        {
            return false;
        }
    }

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "books_id")
    public Set<Books_has_Authors> getBooks_has_authors() {
        return books_has_authors;
    }

    public void setBooks_has_authors(Set<Books_has_Authors> books_has_authors) {
        this.books_has_authors = books_has_authors;
    }

    public Book merge(Book obj)
    {
        if (obj != null)
        {
            this.ID = obj.ID;
            this.title = obj.title;
            this.year = obj.year;
        }
        return this;
    }
}
