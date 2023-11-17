package com.hallym.booker.api;

import com.hallym.booker.domain.*;
import com.hallym.booker.dto.GCP.ResponseUpdateEntity;
import com.hallym.booker.dto.GCP.ResponseUploadDto;
import com.hallym.booker.dto.GCP.ResponseUploadEntity;
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
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
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
    private final GCPService gcpService;

    /**
     * 프로필 등록
     */
    @PostMapping("/profile/{uid}/new") // @RequestBody ProfileInterestDto request
    public SuccessResponse profileRegister(@RequestParam(value = "file", required = false) MultipartFile file, String uid, String nickname, String usermessage,
                                           String uinterest1, String uinterest2, String uinterest3, String uinterest4, String uinterest5)
                                            throws IOException {
//        Profile profile = new Profile(request.getUid(), request.getNickname(), request.getUseriamgeUrl(), request.getUserimageName(), request.getUsermessage());
//        Interests interests = new Interests(request.getUid(), request.getUinterest1(), request.getUinterest2(), request.getUinterest3(), request.getUinterest4(), request.getUinterest5());
        Login login = loginService.findOne(uid);

        //Following, Followers,reports,books,directmessages == null
//        loginservice.join(login);
        SuccessResponse successResponse = new SuccessResponse();
        ResponseUploadDto responseUploadDto;
        if(file == null) {
            System.out.println("여기로 들어오나?");
            responseUploadDto = new ResponseUploadDto("basisImage", "https://storage.googleapis.com/booker-v3/basis-profile.png");
        }
        else {
            responseUploadDto = gcpService.uploadImage(file);
        }
        Profile profile = Profile.create(login, null, null, null, null, null, uid, nickname, responseUploadDto.getImageUrl(), responseUploadDto.getImageName(), usermessage);
        Interests interests = Interests.create(profile, uid, uinterest1, uinterest2, uinterest3, uinterest4, uinterest5);
        profileService.join(profile);
        interestsService.saveInterests(interests);
        successResponse.setData("Register login Success");
        return successResponse;
    }

    /**
     * 프로필 수정
     */
    @PutMapping("/profile/{uid}/edit")
    public ProfileUpdateResponse profileEditForm(@RequestParam(value = "file", required = false) MultipartFile file,
                                                 @PathVariable String uid,
                                                 @RequestParam(required = false) String usermessage,
                                                 @RequestParam(required = false) String uinterest1,
                                                 @RequestParam(required = false) String uinterest2,
                                                 @RequestParam(required = false) String uinterest3,
                                                 @RequestParam(required = false) String uinterest4,
                                                 @RequestParam(required = false) String uinterest5) throws IOException {
        Profile profile = profileService.findOne(uid);
        Interests interests = interestsService.findInterests(uid);

        String tempImageUrl = "";
        String tempImageName = "";
        ResponseUploadDto responseUploadDto = new ResponseUploadDto("", "");
        System.out.println(profile.getUserimageName().isEmpty());
        if(file != null) {
            if(file.getName().equals("basis-image") || profile.getUserimageName().isEmpty()) {
                responseUploadDto = gcpService.uploadImage(file);
            }
            else {
                responseUploadDto = gcpService.updateImage(file, profile.getUserimageName());
            }
            tempImageUrl = responseUploadDto.getImageUrl();
            tempImageName = responseUploadDto.getImageName();
        } else {
            tempImageUrl = profile.getUseriamgeUrl();
            tempImageName = profile.getUserimageName();
        }

        profileService.updateProfile(uid, tempImageUrl, tempImageName, usermessage);
        interestsService.updateInterests(uid, uinterest1, uinterest2, uinterest3, uinterest4, uinterest5);
        return new ProfileUpdateResponse("Profile Update Success");
    }

    /**
     * 프로필 수정
     */
    /*@PutMapping("/profile/{uid}/edit")
    public SuccessResponse profileEdit(@PathVariable("uid") String uid, @RequestBody ProfileRequest request) {
        profileService.updateProfile(uid, request.getUseriamgeUrl(), request.getUserimageName(), request.getUsermessage());
        interestsService.updateInterests(uid, request.getUinterest1(), request.getUinterest2(), request.getUinterest3(), request.getUinterest4(), request.getUinterest5());
        SuccessResponse successResponse = new SuccessResponse();
        successResponse.setData("Edit Profile Success");
        return successResponse;
    }*/

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
    public ProfileInterestDto profileFind(@PathVariable("uid") String uid) {
        Profile profile = profileService.findOne(uid);
        ProfileInterestDto profileInterestDto = new ProfileInterestDto();
        profileInterestDto.setUid(profile.getUid());
        profileInterestDto.setNickname(profile.getNickname());
        profileInterestDto.setUsermessage(profile.getUsermessage());
        profileInterestDto.setUseriamgeUrl(profile.getUseriamgeUrl());
        profileInterestDto.setUserimageName(profile.getUserimageName());

        Interests interests = interestsService.findInterests(uid);
        profileInterestDto.setUinterest1(interests.getUinterest1());
        profileInterestDto.setUinterest2(interests.getUinterest2());
        profileInterestDto.setUinterest3(interests.getUinterest3());
        profileInterestDto.setUinterest4(interests.getUinterest4());
        profileInterestDto.setUinterest5(interests.getUinterest5());

        return profileInterestDto;
    }

    @GetMapping("/profile/find/interests/{uid}")
    public InterestsFindResponse profileInterestsFind(@PathVariable("uid") String uid) {
        Interests interests = interestsService.findInterests(uid);

        return new InterestsFindResponse(interests.getUinterest1(), interests.getUinterest2(), interests.getUinterest3(),
                interests.getUinterest4(), interests.getUinterest5());
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
