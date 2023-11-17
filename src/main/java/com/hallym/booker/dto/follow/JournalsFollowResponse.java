package com.hallym.booker.dto.follow;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class JournalsFollowResponse {
    private String toUserId; // ㅇㅇ에게, 팔로잉/팔로워 당하는 사람
    private String fromUserId; // ㅇㅇ이가, 팔로잉/팔로워 가하는 사람

    private String jid; //yyyy:mm:ff:hh:mm:ss:000
    private LocalDateTime pdatetime;
    private String ptitle;
    private String pcontents;

    private String nickname;
    private String userimageUrl;
    private String userimageName;

    public JournalsFollowResponse() {
    }
}
