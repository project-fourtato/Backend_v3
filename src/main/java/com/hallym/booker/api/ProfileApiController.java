package com.hallym.booker.api;

import com.hallym.booker.domain.*;
import com.hallym.booker.dto.Login.LoginCheckResponse;
import com.hallym.booker.dto.Login.LoginDto;
import com.hallym.booker.dto.Login.LoginRequest;
import com.hallym.booker.dto.Profile.*;
import com.hallym.booker.dto.Result;
import com.hallym.booker.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class ProfileApiController {
    private final ProfileService profileService;
    private final InterestsService interestsService;
    private final LoginService loginService;
    private final FollowService followService;
    private final ReportsService reportsService;
    private final BooksService booksService;
    private final DirectmessageService directmessageService;
    private final JournalsService journalsService;

    /**
     * 프로필 등록
     */
    @PostMapping("/profile/{uid}/new")
    public SuccessResponse profileRegister(@RequestBody ProfileInterestDto request) {
//        Profile profile = new Profile(request.getUid(), request.getNickname(), request.getUseriamgeUrl(), request.getUserimageName(), request.getUsermessage());
//        Interests interests = new Interests(request.getUid(), request.getUinterest1(), request.getUinterest2(), request.getUinterest3(), request.getUinterest4(), request.getUinterest5());
        Login login = loginService.findOne(request.getUid());

        //Following, Followers,reports,books,directmessages == null
//        loginservice.join(login);
        Profile profile = Profile.create(login, null, null, null, null, null, request.getUid(), request.getNickname(), request.getUseriamgeUrl(), request.getUserimageName(), request.getUsermessage());
        Interests interests = Interests.create(profile, request.getUid(), request.getUinterest1(), request.getUinterest2(), request.getUinterest3(), request.getUinterest4(), request.getUinterest5());
        profileService.join(profile);
        interestsService.saveInterests(interests);
        SuccessResponse successResponse = new SuccessResponse();
        successResponse.setData("Register login Success");
        return successResponse;
    }

    /**
     * 프로필 수정 폼
     */
    @GetMapping("/profile/{uid}/edit")
    public ProfileInterestDto profileEditForm(@PathVariable("uid") String uid) {
        Profile profile = profileService.findOne(uid);
        Interests interests = interestsService.findInterests(uid);
        ProfileInterestDto profileInterestDto = new ProfileInterestDto();
        profileInterestDto.setUid(profile.getUid());
        profileInterestDto.setNickname(profile.getNickname());
        profileInterestDto.setUsermessage(profile.getUsermessage());
        profileInterestDto.setUseriamgeUrl(profile.getUseriamgeUrl());
        profileInterestDto.setUserimageName(profile.getUserimageName());
        profileInterestDto.setUinterest1(interests.getUinterest1());
        profileInterestDto.setUinterest2(interests.getUinterest2());
        profileInterestDto.setUinterest3(interests.getUinterest3());
        profileInterestDto.setUinterest4(interests.getUinterest4());
        profileInterestDto.setUinterest5(interests.getUinterest5());

        return profileInterestDto;
    }

    /**
     * 프로필 수정
     */
    @PutMapping("/profile/{uid}/edit")
    public SuccessResponse profileEdit(@PathVariable("uid") String uid, @RequestBody ProfileRequest request) {
        profileService.updateProfile(uid, request.getUseriamgeUrl(), request.getUserimageName(), request.getUsermessage());
        interestsService.updateInterests(uid, request.getUinterest1(), request.getUinterest2(), request.getUinterest3(), request.getUinterest4(), request.getUinterest5());
        SuccessResponse successResponse = new SuccessResponse();
        successResponse.setData("Edit Profile Success");
        return successResponse;
    }

    /**
     * 프로필 삭제
     */
    // http://localhost:8080/profile/user1/delete
    @PostMapping("/profile/{uid}/delete")
    public ResponseEntity<String> profileDelete(@PathVariable("uid") String uid) {
        try {
            List<Directmessage> directmessageList1 = directmessageService.findAllDirectMessagesByRecipient(uid);
            for(int i=0;i<directmessageList1.size();i++){
                directmessageService.deleteDirectmessage(directmessageList1.get(i));
            }
            List<Directmessage> directmessageList2 = directmessageService.findAllDirectMessagesBySender(uid);
            for(int i=0;i<directmessageList2.size();i++){
                directmessageService.deleteDirectmessage(directmessageList2.get(i));
            }
            List<Interests> InterestsList = interestsService.findAllInterestsByProfile(uid);
            for(int i=0;i<InterestsList.size();i++) {
                interestsService.deleteInterests(InterestsList.get(i));
            }
            List<Books> booksList = booksService.findAllBooksByProfile(uid);
            for(int i=0;i<booksList.size();i++){
                List<Journals> journalsList = journalsService.findAllJournalsByUserbid(booksList.get(i).getUserbid());
                for(int j=0;j<journalsList.size();j++){
                    journalsService.deleteJournals(journalsList.get(j));
                }
            }
            List<Reports> reportsList = reportsService.findAllByUid(uid);
            for(int i=0;i<reportsList.size();i++) {
                reportsService.deleteOne(reportsList.get(i));
            }
            List<Follow> followingList = followService.findAllTo(uid);
            for(int i=0;i<followingList.size();i++) {
                followService.deleteFollow(followingList.get(i));
            }
            List<Follow> followerList = followService.findAllFrom(uid);
            for(int i=0;i<followerList.size();i++) {
                followService.deleteFollow(followerList.get(i));
            }
            for(int i=0;i<booksList.size();i++) {
                booksService.deleteBooks(booksList.get(i));
            }
            Login login = loginService.findOne(uid);
            loginService.deleteTableWithForeignKeyChecks(login);

            return ResponseEntity.ok("Profile and associated data deleted successfully");
        } catch (Exception e) {
            e.printStackTrace(); // Log the exception for debugging
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error deleting profile");
        }
    }

    /**
     * 프로필 조회
     */
    @GetMapping("/profile/{uid}")
    public ProfileDto profileFind(@PathVariable("uid") String uid) {
        Profile profile = profileService.findOne(uid);
        ProfileDto profileDto = new ProfileDto();
        profileDto.setUid(profile.getUid());
        profileDto.setNickname(profile.getNickname());
        profileDto.setUsermessage(profile.getUsermessage());
        profileDto.setUseriamgeUrl(profile.getUseriamgeUrl());
        profileDto.setUserimageName(profile.getUserimageName());
        return profileDto;
    }

    /**
     * 관심사가 동일한 프로필 목록 조회
     */
    @GetMapping("/profile/interests/{uid}")
    public Result sameInterestsList(@PathVariable("uid") String uid){
        List<Profile> profiles = interestsService.sameInterests(interestsService.findInterests(uid));
        List<ProfileInterestDto> profileInterestDtos = new ArrayList<>();
        for(int i=0;i<profiles.size();i++){
            Profile profile = profiles.get(i);
            Interests interests = interestsService.findInterests(profile.getUid());
            ProfileInterestDto p = new ProfileInterestDto(
                    profile.getUid(),
                    profile.getNickname(),
                    profile.getUseriamgeUrl(),
                    profile.getUserimageName(),
                    profile.getUsermessage(),
                    interests.getUinterest1(),
                    interests.getUinterest2(),
                    interests.getUinterest3(),
                    interests.getUinterest4(),
                    interests.getUinterest5());
            profileInterestDtos.add(p);
        }

        return new Result(profileInterestDtos);
    }

    /**
     * 유저 검색에서 유저 닉네임을 통해 조회
     */
    @GetMapping("/profile/search/{nickname}")
    public Result profileSearch(@PathVariable("nickname") String nickname){
        List<Profile> profile= profileService.findByNickname(nickname);
        List<ProfileDto> profileDtoList = new LinkedList<>();
        for(int i=0;i<profile.size();i++){
            ProfileDto profileDto = new ProfileDto();
            profileDto.setUid(profile.get(i).getUid());
            profileDto.setNickname(profile.get(i).getNickname());
            profileDto.setUsermessage(profile.get(i).getUsermessage());
            profileDto.setUseriamgeUrl(profile.get(i).getUseriamgeUrl());
            profileDto.setUserimageName(profile.get(i).getUserimageName());
            profileDtoList.add(profileDto);
        }
        return new Result(profileDtoList);
    }

    /**
     * 닉네임 중복 검사
     */
    @GetMapping("/profile/checkNickname/{nickname}")
    public ProfileCheckResponse nicknameCheck(@PathVariable("nickname") String nickname){
        Boolean result = profileService.checkNickname(nickname);
        ProfileCheckResponse profileCheckResponse = new ProfileCheckResponse();
        profileCheckResponse.setData(result);
        return profileCheckResponse;
    }
}
