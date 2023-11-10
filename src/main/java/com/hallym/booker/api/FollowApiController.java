package com.hallym.booker.api;

import com.hallym.booker.domain.*;
import com.hallym.booker.dto.Result;
import com.hallym.booker.dto.follow.*;
import com.hallym.booker.service.FollowService;
import com.hallym.booker.service.ProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class FollowApiController {

    private final FollowService followService;
    private final ProfileService profileService;

    // 팔로잉 & 팔로워 추가
    // http://localhost:8080/follow/new?toUserId=hamji&fromUserId=poipoi
    @PostMapping("/follow/new")
    public FollowDto addFollow(@RequestParam String toUserId, @RequestParam String fromUserId) {
        // 팔로우 추가를 컨트롤러에서 처리하고 데이터베이스에 저장
        Profile profile = profileService.findOne(fromUserId);

        Follow follow = Follow.create(profile, toUserId);
        System.out.println(profile.getNickname());

        followService.saveFollow(follow);

        // 성공 메시지 반환
        return new FollowDto(toUserId, fromUserId);
    }

    // fromUserId가 팔로잉 하는 수 조회 (전체)
    // http://localhost:8080/follow/followingsCount/poipoi
    @GetMapping("/follow/followingsCount/{fromUserId}")
    public FromUserIdCountDto followingsCount(@PathVariable("fromUserId") String fromUserId) {
        System.out.println("fromUserId = " + fromUserId);
        Long findFollowingsCounts = followService.countByFromUserId(fromUserId);

        return new FromUserIdCountDto(Integer.toString(Math.toIntExact(findFollowingsCounts)));
    }

    // toUserId를 팔로워 하는 수 조회 (전체)
    // http://localhost:8080/follow/follwersCount/yarong
    @GetMapping("/follow/followersCount/{toUserId}")
    public ToUserIdCountDto followersCount(@PathVariable("toUserId") String toUserId) {
        Long findFollowersCounts = followService.countByToUserId(toUserId);

        return new ToUserIdCountDto(Integer.toString(Math.toIntExact(findFollowersCounts)));
    }

    // fromUserId가 팔로잉 하는 목록 조회 (전체)
    // http://localhost:8080/follow/followingsList/yarong
    @GetMapping("/follow/followingsList/{fromUserId}")
    public Result followingsList(@PathVariable("fromUserId") String fromUserId) {
        List<Follow> findFollowings = followService.findAllTo(fromUserId);
        List<FollowListDto> collect = findFollowings.stream()
                .map(m -> new FollowListDto(m.getToUserId()))
                .collect(Collectors.toList());

        return new Result(collect);
    }

    // 팔로잉  & 팔로워 삭제
    // http://localhost:8080/follow/delete?toUserId=hamji&fromUserId=yarong
    @PostMapping("/follow/delete")
    public FollowDeleteResponse deleteFollow(@RequestParam String toUserId, @RequestParam String fromUserId) {
        Follow follow = followService.findOneTo(fromUserId, toUserId);
        if (follow != null && follow.getToUserId().equals(toUserId)) {
            followService.deleteFollow(follow);
            return new FollowDeleteResponse("Followings deleted successfully");
        } else {
            return new FollowDeleteResponse("Followings deleted error");
        }
    }



}