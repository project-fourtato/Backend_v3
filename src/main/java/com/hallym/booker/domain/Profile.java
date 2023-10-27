package com.hallym.booker.domain;

import lombok.Getter;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
public class Profile {
    private String uid;
    private String nickname;
    private String useriamgeUrl;
    private String userimagePath;
    private String usermessage;

    @OneToMany(mappedBy = "profile")
    private List<Books> books = new ArrayList<>();

    @OneToMany(mappedBy = "profile")
    private List<Interests> interests = new ArrayList<>();

    public Profile() {}
    public Profile(String uid, String nickname, String useriamgeUrl, String userimagePath, String usermessage) {
        this.uid = uid;
        this.nickname = nickname;
        this.useriamgeUrl = useriamgeUrl;
        this.userimagePath = userimagePath;
        this.usermessage = usermessage;
    }
}
