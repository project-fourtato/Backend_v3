package com.hallym.booker.dto.follow;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class FollowListDto {
    private String toUserId; // ㅇㅇ에게, 팔로잉/팔로워 당하는 사람
}