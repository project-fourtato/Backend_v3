package com.hallym.booker.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Getter @Setter
public class LibraryList {
    @Id
    private String libCode;
    private String libName;
}
