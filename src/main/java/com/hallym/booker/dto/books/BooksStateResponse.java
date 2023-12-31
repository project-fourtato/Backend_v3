package com.hallym.booker.dto.books;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class BooksStateResponse {
    private String userbid;
    private int bookState;
    private int saleState;

    public BooksStateResponse() {
    }
}
