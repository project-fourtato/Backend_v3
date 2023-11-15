package com.hallym.booker.api;

import com.hallym.booker.domain.Directmessage;
import com.hallym.booker.domain.Profile;
import com.hallym.booker.dto.Directmessages.*;
import com.hallym.booker.dto.Result;
import com.hallym.booker.service.DirectmessageService;
import com.hallym.booker.service.ProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class DirectmessagesApiController {
    private final DirectmessageService directmessageService;

    @RestController
    @RequiredArgsConstructor
    public class DirectmessageApiController {

        private final DirectmessageService directmessageService;
        private final ProfileService profileService;

        // 쪽지 등록
        //쪽지id 자동생성할 수 있도록 코드를 짜줘야 함
        @PostMapping("/directmessages/new")
        public Result addDirectmessage(@RequestBody DirectmessageDto request) {
            Profile profile = profileService.findOne(request.getSenderuid());
            Directmessage d = Directmessage.create(profile, request.getSenderuid(),
                    request.getRecipientuid(),
                    LocalDateTime.now().withNano(0),
                    0,
                    request.getMtitle(),
                    request.getMcontents());
            directmessageService.saveDirectmessage(d);
            return new Result("Directmessage added Success");
        }

        // 쪽지 목록 조회(프로필과 함께)
        @GetMapping("/profile-directm/{uid}")
        public Result directmessageList(@PathVariable("uid") String uid) {

            List<Directmessage> findAllDirectmessage1 = directmessageService.findAllDirectMessagesByRecipient(uid);
            List<DirectmessageResponse> findAll = new LinkedList<>();
            for(int i=0;i<findAllDirectmessage1.size();i++){
                Profile p = profileService.findOne(findAllDirectmessage1.get(i).getSenderuid());
                DirectmessageResponse dr = new DirectmessageResponse(
                        findAllDirectmessage1.get(i).getMessageid(),
                        findAllDirectmessage1.get(i).getSenderuid(),
                        findAllDirectmessage1.get(i).getRecipientuid(),
                        findAllDirectmessage1.get(i).getMdate(),
                        findAllDirectmessage1.get(i).getMcheck(),
                        findAllDirectmessage1.get(i).getMtitle(),
                        findAllDirectmessage1.get(i).getMcontents(),
                        p.getNickname(),
                        p.getUseriamgeUrl(),
                        p.getUserimageName()
                        );
                findAll.add(dr);
            }
            List<Directmessage> findAllDirectmessage2 = directmessageService.findAllDirectMessagesBySender(uid);
            for(int i=0;i<findAllDirectmessage2.size();i++){
                Profile p = profileService.findOne(findAllDirectmessage2.get(i).getRecipientuid());
                DirectmessageResponse dr = new DirectmessageResponse(
                        findAllDirectmessage2.get(i).getMessageid(),
                        findAllDirectmessage2.get(i).getSenderuid(),
                        findAllDirectmessage2.get(i).getRecipientuid(),
                        findAllDirectmessage2.get(i).getMdate(),
                        findAllDirectmessage2.get(i).getMcheck(),
                        findAllDirectmessage2.get(i).getMtitle(),
                        findAllDirectmessage2.get(i).getMcontents(),
                        p.getNickname(),
                        p.getUseriamgeUrl(),
                        p.getUserimageName()
                );
                findAll.add(dr);
            }
            Collections.sort(findAll, (obj1, obj2) -> obj2.getMdate().compareTo(obj1.getMdate())); //내림차순으로 정렬
            return new Result(findAll);
        }

        // 하나의 쪽지 조회
        // http://localhost:8080/directmessages/10/user2
        @GetMapping("/directmessages/{messageid}/{uid}")
        public Result findDirectmessage(@PathVariable("messageid") Long messageid, @PathVariable("uid") String uid) {
            Directmessage findDirectmessage = directmessageService.findDirectmessage(messageid);
            DirectmessageDto d = new DirectmessageDto(messageid,findDirectmessage.getSenderuid(), findDirectmessage.getRecipientuid(), findDirectmessage.getMdate(), findDirectmessage.getMcheck(), findDirectmessage.getMtitle(), findDirectmessage.getMcontents());
            return new Result(d);
        }

        // 쪽지 상태 업데이트 (11/15 추가)
        // http://localhost:8080/directmessages/mcheckUpdate/12
        /*{
               "mcheck": 1
           }
         */
        @PutMapping("/directmessages/mcheckUpdate/{messageid}")
        public UpdateMcheckResponse updateMcheck(@RequestBody UpdateMcheckRequest request, @PathVariable Long messageid) {
            //Directmessage findDirectmessage = directmessageService.findDirectmessage(messageid);
            directmessageService.updateMcheck(messageid, request.getMcheck());

            return new UpdateMcheckResponse("mcheck update success");
        }

        // 쪽지 삭제
        @PostMapping("/directmessages/messageid={messageid}/delete")
        public Result deleteDirectmessage(@PathVariable("messageid") Long messageid) {

            Directmessage directmessage = directmessageService.findDirectmessage(messageid);
            directmessageService.deleteDirectmessage(directmessage);

            // 성공 메시지 반환
            return new Result("Directmessages deleted successfully");
        }

    }
}
