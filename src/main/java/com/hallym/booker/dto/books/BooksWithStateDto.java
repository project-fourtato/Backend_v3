package com.hallym.booker.dto.books;

import lombok.Data;
import lombok.Setter;

@Data
@Setter
public class BooksWithStateDto {
    private String uid;
    private int bookstate;

    private String BookName;
    private String BookAuthor;
    private String isbn;
    private String publisher;
    private String cover;

    public BooksWithStateDto(String bookName, String bookAuthor, String isbn, String publisher, String cover) {
        this.BookName = bookName;
        this.BookAuthor = bookAuthor;
        this.isbn = isbn;
        this.publisher = publisher;
        this.cover = cover;
    }
}
