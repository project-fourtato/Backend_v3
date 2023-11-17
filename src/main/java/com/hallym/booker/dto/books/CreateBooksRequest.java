package com.hallym.booker.dto.books;

import lombok.Data;

@Data
public class CreateBooksRequest {
    private String isbn;
    private int bookstate;
    private int salestate;

    public CreateBooksRequest() {
    }
}
