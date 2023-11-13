package com.hallym.booker.api;

import com.hallym.booker.domain.Books;
import com.hallym.booker.domain.Journals;
import com.hallym.booker.dto.Result;
import com.hallym.booker.dto.books.BestsellerResultDto;
import com.hallym.booker.dto.books.BooksDto;
import com.hallym.booker.dto.books.UpdateBooksStateRequest;
import com.hallym.booker.dto.books.UpdateBooksStateResponse;
import com.hallym.booker.dto.journals.*;
import com.hallym.booker.service.BooksService;
import com.hallym.booker.service.JournalsService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.persistence.MappedSuperclass;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class JournalsApiController {

    private final JournalsService journalsService;
    private final BooksService booksService;

    @GetMapping("/journalsList/{userbid}")
    public Result JournalsList(@PathVariable("userbid") String userbid) {
        List<Journals> findJournals = journalsService.findAllJournalsByUserbid(userbid);
        List<JournalsDto> collect = findJournals.stream()
                .map(m -> new JournalsDto(m.getJid(), m.getPdatetime(), m.getPtitle(), m.getPcontents(), m.getPimageUrl(), m.getPimageName()))
                .collect(Collectors.toList());

        return new Result(collect);
    }

    @PostMapping("/journals/new/{userbid}")
    public CreateJournalsResponse createJournals(@RequestBody CreateMemberRequest request,
                                                 @PathVariable("userbid") String userbid) {
        Books findBooks = booksService.findOneBooks(userbid);
        String create_jid = LocalDateTime.now().toString();

        Journals journals = Journals.create(findBooks, create_jid, LocalDateTime.now().withNano(0),
                request.getPtitle(), request.getPcontents(), request.getPimageUrl(), request.getPimageName());

        journalsService.saveJournals(journals);
        return new CreateJournalsResponse("Write Journals Success");
    }

    @GetMapping("/journals/{jid}/edit")
    public UpdateJournalsFormResponse updateJournalForm(@PathVariable("jid") String jid) {
        //String ptitle, String pcontents, String pimageUrl, String pimageName
        //journalsService.updateJournals(request.getJid(), request.getPtitle(), request.getPcontents(), request.getPimageUrl(), request.getPimageName());
        Journals findJournals = journalsService.findOne(jid);
        return new UpdateJournalsFormResponse(findJournals.getJid(), findJournals.getPdatetime(), findJournals.getPtitle(),
                findJournals.getPcontents(), findJournals.getPimageUrl(), findJournals.getPimageName());
    }

    @PutMapping("/journals/{jid}/edit")
    public UpdateJournalsResponse updateJournal(
            @PathVariable("jid") String jid,
            @RequestBody UpdateJournalsRequest request) {
        journalsService.updateJournals(jid, request.getPtitle(), request.getPcontents(), request.getPimageUrl(), request.getPimageName());
        return new UpdateJournalsResponse("Edit Journals Success");
    }

    @GetMapping("/journals/{jid}")
    public findJournalsResponse findJournals(@PathVariable String jid) {
        Journals findJournals = journalsService.findOne(jid);
        return new findJournalsResponse(findJournals.getJid(), findJournals.getPdatetime(), findJournals.getPtitle(),
                findJournals.getPcontents(), findJournals.getPimageUrl(), findJournals.getPimageName());
    }

    @PostMapping("/journals/{jid}/delete")
    public deleteJournalsResponse deleteJournals(@PathVariable String jid) {
        Journals findJournals = journalsService.findOne(jid);
        journalsService.deleteJournals(findJournals);
        return new deleteJournalsResponse("Delete Books Success");
    }

    @GetMapping("/journals/bookinfo/{isbn}")
    public FindBooksInfoDto findBooksInfo(@RequestBody CreateMemberRequest request,
                                          @PathVariable String isbn) {
        aladinApi aapi = new aladinApi();

        // 본인이 받은 api키를 추가
        String key = "";
        FindBooksInfoDto findBookInfo = new FindBooksInfoDto();

        try {
            // parsing할 url 지정(API 키 포함해서)
            String url = "https://www.aladin.co.kr/ttb/api/ItemLookUp.aspx?ttbkey=ttbwlco13232133003&itemIdType=ISBN13&ItemId=" + isbn + "&output=xml&Version=20131101&OptResult=ebookList,usedList,reviewList";

            DocumentBuilderFactory dbFactoty = DocumentBuilderFactory.newInstance();

            DocumentBuilder dBuilder = dbFactoty.newDocumentBuilder();
            Document doc = dBuilder.parse(url);

            // 제일 첫번째 태그
            doc.getDocumentElement().normalize();

            // 파싱할 tag
            NodeList nList = doc.getElementsByTagName("item");

            Node nNode = nList.item(0);

            Element eElement = (Element) nNode;

            findBookInfo = new FindBooksInfoDto(aapi.getTagValue("title", eElement),
                    aapi.getTagValue("author", eElement),
                    aapi.getTagValue("pubDate", eElement),
                    aapi.getTagValue("description", eElement),
                    aapi.getTagValue("cover", eElement),
                    aapi.getTagValue("categoryName", eElement),
                    aapi.getTagValue("publisher", eElement));

        } catch (Exception e) {
            e.printStackTrace();
        }

        return findBookInfo;
    }

    @PutMapping("/journals/bookstateUpdate/uid={uid}&isbn={isbn}")
    public UpdateBooksStateResponse updateBookState(@RequestBody UpdateBooksStateRequest request,
                                                    @PathVariable String uid, @PathVariable String isbn) {
        Books findBook = booksService.searchBooksByIsbn(uid, isbn);
        booksService.updateBooks(findBook.getUserbid(), request.getBookstate(), findBook.getSalestate());

        return new UpdateBooksStateResponse("bookstate update success");
    }

}
