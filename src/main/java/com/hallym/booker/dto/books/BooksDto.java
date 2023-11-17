package com.hallym.booker.dto.books;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@AllArgsConstructor
public class BooksDto {
    private String userbid;
    private String uid;
    private String isbn;
    private int bookstate;
    private int salestate;

    public BooksDto() {
    }
}
