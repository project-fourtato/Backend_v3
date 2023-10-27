package com.hallym.booker.domain;

import lombok.Getter;
import org.springframework.context.annotation.EnableMBeanExport;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
public class Profile {
    @Id
    private String profileUid;
    private String nickname;
    private String useriamgeUrl;
    private String userimagePath;
    private String usermessage;

    @OneToOne
    @JoinColumn(name = "loginUid")
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
        joinColumns = @JoinColumn(name = "profileUid"),
        inverseJoinColumns = @JoinColumn(name = "messageid"))
    private List<Directmessage> directmessages = new ArrayList<>();

    @ManyToMany
    @JoinTable(name = "profile_reports",
            joinColumns = @JoinColumn(name = "profileUid"),
            inverseJoinColumns = @JoinColumn(name = "reportsUid"))
    private List<Reports> reports = new ArrayList<>();

    public Profile() {}
    public Profile(String profileUid, String nickname, String useriamgeUrl, String userimagePath, String usermessage) {
        this.profileUid = profileUid;
        this.nickname = nickname;
        this.useriamgeUrl = useriamgeUrl;
        this.userimagePath = userimagePath;
        this.usermessage = usermessage;
    }
}
