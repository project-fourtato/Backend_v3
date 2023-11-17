package com.hallym.booker.domain;

import lombok.Getter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
public class Profile {
    @Id
    @Column(name = "profile_uid")
    private String uid;
    private String nickname;
    private String useriamgeUrl;
    private String userimageName;
    private String usermessage;

    @OneToOne
    @JoinColumn(name = "login_uid")
    private Login login;

    @OneToMany(mappedBy = "profile")
    private List<Books> books = new ArrayList<>();

    @OneToMany(mappedBy = "profile")
    private List<Interests> interests = new ArrayList<>();

    @OneToMany(mappedBy = "fromUserId")
    private List<Follow> follow = new ArrayList<>();

    @ManyToMany
    @JoinTable(name = "profile_directm",
            joinColumns = @JoinColumn(name = "profile_uid"),
            inverseJoinColumns = @JoinColumn(name = "messageid"))
    private List<Directmessage> directmessages = new ArrayList<>();

    @ManyToMany
    @JoinTable(name = "profile_reports",
            joinColumns = @JoinColumn(name = "profile_uid"),
            inverseJoinColumns = @JoinColumn(name = "reports_uid"))
    private List<Reports> reports = new ArrayList<>();

    public void setLogin(Login login) {
        this.login = login;
    }

    public void setBooks(Books books) {
        this.books.add(books);
        books.setProfile(this);
    }

    public void setInterests(Interests interests) {
        this.interests.add(interests);
        interests.setProfile(this);
    }

    public void setFollow(Follow follow) {
        this.follow.add(follow);
        follow.setProfile(this);
    }

    public void setDirectmessages(Directmessage directmessages) {
        this.directmessages.add(directmessages);
    }

    public void setReports(Reports reports) {
        this.reports.add(reports);
    }

    public Profile() {}
    public Profile(String uid, String nickname, String useriamgeUrl, String userimageName, String usermessage) {
        this.uid = uid;
        this.nickname = nickname;
        this.useriamgeUrl = useriamgeUrl;
        this.userimageName = userimageName;
        this.usermessage = usermessage;
    }

    //==수정 메서드==//
    public Profile change(String useriamgeUrl, String userimageName, String usermessage) {
        this.useriamgeUrl = useriamgeUrl;
        this.userimageName = userimageName;
        this.usermessage = usermessage;

        return this;
    }

    //==생성 메서드==//
    public static Profile create(Login login, Reports reports, Follow follow, Interests interests, Books books, Directmessage directmessage, String uid, String nickname, String useriamgeUrl, String userimageName, String usermessage){
        Profile profile = new Profile(uid, nickname, useriamgeUrl, userimageName, usermessage);
        if (books != null) {
            profile.setBooks(books);
        }
        if (directmessage != null) {
            profile.setDirectmessages(directmessage);
        }
        if (follow != null) {
            profile.setFollow(follow);
        }
        profile.setLogin(login);
        if(interests != null) {
            profile.setInterests(interests);
        }
        if(reports != null) {
            profile.setReports(reports);
        }
        return profile;
    }

}
