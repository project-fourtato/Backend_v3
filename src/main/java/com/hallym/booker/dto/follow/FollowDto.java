package com.hallym.booker.dto.follow;

import com.hallym.booker.domain.Profile;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class FollowDto {

    private String toUserId; // ㅇㅇ에게, 팔로잉/팔로워 당하는 사람
    private String fromUserId; // ㅇㅇ이가, 팔로잉/팔로워 가하는 사람

    public FollowDto() {
    }
}