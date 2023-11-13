package com.hallym.booker.dto.follow;

import lombok.Data;

import java.util.List;

@Data
public class FollowResult<T> {
    private List<T> followData;

    public FollowResult(List<T> followData) {
        this.followData = followData;
    }
}
