package com.hallym.booker.domain;

import com.mysql.cj.log.Log;
import lombok.Getter;
import org.springframework.context.annotation.EnableMBeanExport;

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
    private String userimagePath;
    private String usermessage;

    @OneToOne
    @JoinColumn(name = "login_uid")
    private Login login;

    @OneToMany(mappedBy = "profile")
    private List<Books> books = new ArrayList<>();

    @OneToMany(mappedBy = "profile")
    private List<Interests> interests = new ArrayList<>();

    @OneToMany(mappedBy = "profile")
    private List<Followers> followeruid = new ArrayList<>();

    @OneToMany(mappedBy = "profile")
    private List<Followings> followinguid = new ArrayList<>();

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

    public void setFolloweruid(Followers followeruid) {
        this.followeruid.add(followeruid);
        followeruid.setProfile(this);
    }

    public void setFollowinguid(Followings followinguid) {
        this.followinguid.add(followinguid);
        followinguid.setProfile(this);
    }

    public Profile() {}
    public Profile(String uid, String nickname, String useriamgeUrl, String userimagePath, String usermessage) {
        this.uid = uid;
        this.nickname = nickname;
        this.useriamgeUrl = useriamgeUrl;
        this.userimagePath = userimagePath;
        this.usermessage = usermessage;
    }

    //==수정 메서드==//
    public Profile change(String useriamgeUrl, String userimagePath, String usermessage) {
        this.useriamgeUrl = useriamgeUrl;
        this.userimagePath = userimagePath;
        this.usermessage = usermessage;

        return this;
    }
}
