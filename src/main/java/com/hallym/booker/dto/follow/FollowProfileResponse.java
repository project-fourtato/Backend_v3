package com.hallym.booker.dto.follow;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class FollowProfileResponse {
    private String toUserId; // ㅇㅇ에게, 팔로잉/팔로워 당하는 사람
    private String fromUserId; // ㅇㅇ이가, 팔로잉/팔로워 가하는 사람

    private String nickname;
    private String userimageUrl;
    private String userimageName;
    private String usermessage;

    public FollowProfileResponse() {
    }
}
