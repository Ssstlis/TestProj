package com.test.entyties;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import javax.persistence.*;
import java.io.Serializable;
import java.util.*;

@Entity
@Table(name = "authors")
@JsonRootName(value = "author")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Author implements Serializable
{
    @JsonProperty(value = "id")
    private Integer ID;
    @JsonProperty(value = "firstname")
    private String firstName;
    @JsonProperty(value = "lastname")
    private String lastName;
    @JsonProperty(value = "middlename")
    private String middleName;
    @JsonProperty(value = "fullname")
    private String fullName;
    @JsonIgnore
    private Set<Books_has_Authors> books_has_authors = new HashSet<>();

    public void cleanup()
    {
        if (firstName != null)
            firstName = firstName.trim();
        if (lastName != null)
            lastName = lastName.trim();
        if (middleName != null)
            middleName = middleName.trim();
        if (fullName != null)
            fullName = fullName.trim();
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="author_id", nullable=false, length=45)
    public Integer getID() {
        return ID;
    }

    public void setID(Integer ID) {
        this.ID = ID;
    }

    @Basic
    @Column(name="firstname", nullable=false, length=45)
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    @Basic
    @Column(name="lastname", nullable=true, length=45)
    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @Basic
    @Column(name="middlename", nullable=true, length=45)
    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    @Basic
    @Column(name="fullname", nullable=true, length=45)
    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }
    @Override
    public String toString()
    {
        return new StringBuilder().append("Author :[ID:")
                .append(this.ID)
                .append(" FN:")
                .append(this.firstName)
                .append(" LN:")
                .append(this.lastName)
                .append(" MN:")
                .append(this.middleName)
                .append(" FN:")
                .append(this.fullName)
                .append("]")
                .toString();
    }

    @Override
    public boolean equals(Object obj)
    {
        if (this == obj)
            return true;
        if (obj == null || getClass() != obj.getClass())
            return false;
        Author other = (Author) obj;
        try
        {
            return ((firstName == null || other.firstName == null) || firstName.equalsIgnoreCase(other.firstName))
                    && (lastName == null || other.lastName == null) || lastName.equalsIgnoreCase(other.lastName)
                    && (middleName == null || other.middleName == null) || middleName.equalsIgnoreCase(other.middleName)
                    && (fullName == null || other.fullName == null) || fullName.equalsIgnoreCase(other.fullName);
        }
        catch (Exception e)
        {
            return false;
        }
    }
    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "authors_id")
    public Set<Books_has_Authors> getBooks_has_authors() {
        return books_has_authors;
    }

    public void setBooks_has_authors(Set<Books_has_Authors> books_has_authors) {
        this.books_has_authors = books_has_authors;
    }

    public Author merge(Author obj)
    {
        if (obj != null)
        {
            this.ID = obj.ID;
            this.fullName = obj.fullName;
            this.middleName = obj.middleName;
            this.firstName = obj.firstName;
        }
        return this;
    }
}
