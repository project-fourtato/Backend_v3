package com.hallym.booker.api;

import com.hallym.booker.domain.*;
import com.hallym.booker.dto.Result;
import com.hallym.booker.dto.follow.*;
import com.hallym.booker.service.BooksService;
import com.hallym.booker.service.FollowService;
import com.hallym.booker.service.JournalsService;
import com.hallym.booker.service.ProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequiredArgsConstructor
public class FollowApiController {

    private final FollowService followService;
    private final JournalsService journalsService;
    private final ProfileService profileService;
    private final BooksService booksService;

    // 팔로잉 & 팔로워 추가
    // http://localhost:8080/follow/new?toUserId=hamji&fromUserId=poipoi
    @PostMapping("/follow/new")
    public FollowCreateResponse addFollow(@RequestParam String toUserId, @RequestParam String fromUserId) {
        // 팔로우 추가를 컨트롤러에서 처리하고 데이터베이스에 저장
        Profile profile = profileService.findOne(fromUserId);
        Follow follow = Follow.create(profile, toUserId);
        followService.saveFollow(follow);

        // 성공 메시지 반환
        return new FollowCreateResponse("Follow created successfully");
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
    // http://localhost:8080/follow/followersCount/yarong
    @GetMapping("/follow/followersCount/{toUserId}")
    public ToUserIdCountDto followersCount(@PathVariable("toUserId") String toUserId) {
        Long findFollowersCounts = followService.countByToUserId(toUserId);

        return new ToUserIdCountDto(Integer.toString(Math.toIntExact(findFollowersCounts)));
    }

    /* 나중에 필요하면 쓰기
    // fromUserId가 팔로잉 하는 목록 조회 - FromUserId와 ToUserId만(전체)
    //http://localhost:8080/follow/followingsList/yarong
    @GetMapping("/follow/followingsList/{fromUserId}")
    public Result followingsList(@PathVariable("fromUserId") String fromUserId) {
        List<Follow> findFollowings = followService.findAllTo(fromUserId);
        List<FollowListDto> collect = findFollowings.stream()
                .map(m -> new FollowListDto(m.getToUserId()))
                .collect(Collectors.toList());

        return new Result(collect);
    }*/

    // fromUserId가 팔로잉 하는 목록 조회 - 프로필(사진 이름, URL)&닉네임 (전체)
    // http://localhost:8080/follow/followingsList/yarong
    @GetMapping("/follow/followingsList/{fromUserId}")
    public Result followingsList(@PathVariable("fromUserId") String fromUserId) {
        // fromUserId가 팔로잉 하는 목록 조회
        List<Follow> followings = followService.findAllTo(fromUserId);
        List<FollowProfileResponse> findAll1 = new LinkedList<>();
        for (int i = 0; i < followings.size(); i++) {
            Profile p = profileService.findOne(followings.get(i).getToUserId());
            FollowProfileResponse fj1 = new FollowProfileResponse(
                    followings.get(i).getToUserId(),
                    followings.get(i).getFromUserId().getUid(),
                    p.getNickname(),
                    p.getUseriamgeUrl(),
                    p.getUserimageName()
            );
            findAll1.add(fj1);
        }
        return new Result(findAll1);
    }

    // toUserId를 팔로워 하는 목록 조회 - 프로필(사진 이름, URL)&닉네임 (전체)
    // http://localhost:8080/follow/followersList/yarong
    @GetMapping("/follow/followersList/{toUserId}")
    public Result followersList(@PathVariable("toUserId") String toUserId) {
        // fromUserId가 팔로잉 하는 목록 조회
        List<Follow> followers = followService.findAllFrom(toUserId);
        List<FollowProfileResponse> findAll2 = new LinkedList<>();
        for (int i = 0; i < followers.size(); i++) {
            Profile p = profileService.findOne(followers.get(i).getFromUserId().getUid());
            FollowProfileResponse fj2 = new FollowProfileResponse(
                    followers.get(i).getFromUserId().getUid(),
                    followers.get(i).getToUserId(),
                    p.getNickname(),
                    p.getUseriamgeUrl(),
                    p.getUserimageName()
            );
            findAll2.add(fj2);
        }
        return new Result(findAll2);
    }

    // fromUserId가 팔로잉 하는 목록의 최신 독서록 목록 조회 - 프로필(사진 이름, URL)&닉네임, 독서록 제목&날짜 랜덤 5개 (전체)
    // http://localhost:8080/follow/followingsLatestJournals/poipoi
    @GetMapping("/follow/followingsLatestJournals/{fromUserId}")
    public FollowResult<JournalsFollowResponse> followingsLatestJournals(@PathVariable("fromUserId") String fromUserId) {
        // fromUserId가 팔로잉 하는 목록 조회
        List<Follow> followings = followService.findAllTo(fromUserId);

        // 팔로잉 중인 각 toUserId에 대해 최신 독서록을 가져와서 리스트에 추가
        List<JournalsFollowResponse> followJournalsResponses = new LinkedList<>();
        for (Follow follow : followings) {
            Profile p = profileService.findOne(follow.getToUserId());
            List<Books> allBooksByProfile = booksService.findAllBooksByProfile(p.getUid());
            for (Books books : allBooksByProfile) {
                List<Journals> userLatestJournals = journalsService.findLatestJournalByUser(books.getUserbid());
                for (Journals journals : userLatestJournals) {
                    if (!userLatestJournals.isEmpty()) {
                        // 최신 독서록을 JournalsFollowResponse로 변환하여 리스트에 추가
                        journals = userLatestJournals.get(0); // 여기서는 첫 번째 독서록만 사용
                        JournalsFollowResponse jfr = new JournalsFollowResponse(
                                follow.getToUserId(),
                                follow.getFromUserId().getUid(),
                                journals.getJid(),
                                journals.getPdatetime(),
                                journals.getPtitle(),
                                journals.getPcontents(),
                                p.getNickname(),
                                p.getUseriamgeUrl(),
                                p.getUserimageName()
                        );
                        followJournalsResponses.add(jfr);
                    }
                }
            }
        }

        // 최대 7개의 항목을 선택 (만약 리스트 크기가 5개보다 작으면 전체 리스트 반환)
        int numberOfJournalsToShow = Math.min(7, followJournalsResponses.size());
        List<JournalsFollowResponse> randomJournals = followJournalsResponses.subList(0, numberOfJournalsToShow);

        // 최종 결과를 반환
        return new FollowResult<>(randomJournals);

    }

    // 팔로잉  & 팔로워 삭제
    // http://localhost:8080/follow/delete?toUserId=hamji&fromUserId=yarong
    @PostMapping("/follow/delete")
    public FollowDeleteResponse deleteFollow(@RequestParam String toUserId, @RequestParam String fromUserId) {
        Follow follow = followService.findOneTo(fromUserId, toUserId);
        if (follow != null && follow.getToUserId().equals(toUserId)) {
            followService.deleteFollow(follow);
            return new FollowDeleteResponse("Follow deleted successfully");
        } else {
            return new FollowDeleteResponse("Follow deleted error");
        }
    }



}