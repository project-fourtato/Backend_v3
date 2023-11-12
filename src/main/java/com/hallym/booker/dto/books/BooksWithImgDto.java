package com.hallym.booker.dto.books;

import com.hallym.booker.domain.Profile;
import lombok.Data;

import java.util.List;

@Data
public class BooksWithImgDto {
    private String userbid;
    private String uid;
    private String isbn;
    private int bookstate;
    private int salestate;
    private String cover; //책 겉표지
    private String author;
    private String title;
    private String publisher;
    private List<ProfileIsbnDto> profile;

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

    public void setProfile(List<ProfileIsbnDto> profile) {
        this.profile = profile;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }
}
