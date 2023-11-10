package com.hallym.booker.dto.books;

import lombok.Data;

@Data
public class BooksWithImgDto {
    private String userbid;
    private String uid;
    private String isbn;
    private int bookstate;
    private int salestate;
    private String cover; //책 겉표지

    public BooksWithImgDto(String userbid, String uid, String isbn, int bookstate, int salestate) {
        this.userbid = userbid;
        this.uid = uid;
        this.isbn = isbn;
        this.bookstate = bookstate;
        this.salestate = salestate;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }
}
