package com.test.entyties;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import java.util.List;

@JsonRootName(value = "response")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResponseBooks {

    @JsonProperty(value = "books")
    private List<RequestBook> books;

    public List<RequestBook> getBooks() {
        return books;
    }

    public void setBooks(List<RequestBook> books) {
        this.books = books;
    }
}
