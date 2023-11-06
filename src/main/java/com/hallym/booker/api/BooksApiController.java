package com.hallym.booker.api;

import com.hallym.booker.domain.Books;
import com.hallym.booker.domain.Journals;
import com.hallym.booker.domain.Profile;
import com.hallym.booker.dto.Result;
import com.hallym.booker.dto.books.*;
import com.hallym.booker.service.BooksService;
import com.hallym.booker.service.JournalsService;
import com.hallym.booker.service.ProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class BooksApiController {

    private final BooksService booksService;
    private final ProfileService profileService;
    private final JournalsService journalsService;

    @GetMapping("/bestseller")
    public Result aladinBestseller() {
        aladinApi aapi = new aladinApi();

        // 본인이 받은 api키를 추가
        String key = "";
        List<BestsellerResultDto> collect = new ArrayList<>();

        try{
            // parsing할 url 지정(API 키 포함해서)
            String url = "http://www.aladin.co.kr/ttb/api/ItemList.aspx?ttbkey=ttbwlco13232133003&QueryType=ItemNewAll&MaxResults=10&start=1&SearchTarget=Book&output=xml&Version=20131101";

            DocumentBuilderFactory dbFactoty = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactoty.newDocumentBuilder();
            Document doc = dBuilder.parse(url);

            // 제일 첫번째 태그
            doc.getDocumentElement().normalize();

            // 파싱할 tag
            NodeList nList = doc.getElementsByTagName("item");

            for(int temp = 0; temp < nList.getLength(); temp++){
                Node nNode = nList.item(temp);

                Element eElement = (Element) nNode;

                BestsellerResultDto br = new BestsellerResultDto(aapi.getTagValue("title", eElement), aapi.getTagValue("author", eElement));
                collect.add(br);
            }

        } catch (Exception e){
            e.printStackTrace();
        }

        return new Result(collect);
    }

    @PostMapping("/search/books/new/{uid}")
    public CreateBooksResponse createBooks(
            @PathVariable("uid") String uid,
            @RequestBody CreateBooksRequest request) {
        Profile findProfile = profileService.findOne(uid);
        String createUserbid = request.getIsbn().concat(uid);

        Books books = Books.create(findProfile, null,
                createUserbid, request.getIsbn(), request.getBookstate(), request.getSalestate());

        booksService.saveBooks(books);
        return new CreateBooksResponse("Books Registration Success");
    }

    @GetMapping("/booksList/{uid}")
    public Result booksList(@PathVariable("uid") String id) {
        List<Books> findBooks = booksService.findAllBooksByProfile(id);
        List<BooksDto> collect = findBooks.stream()
                .map(m -> new BooksDto(m.getUserbid(), id, m.getIsbn(), m.getBookstate(), m.getSalestate()))
                .collect(Collectors.toList());

        return new Result(collect);
    }

    @GetMapping("/booksState/{userbid}")
    public BooksStateResponse booksState(@PathVariable("userbid") String userbid) {
        Books findBooks = booksService.findOneBooks(userbid);

        return new BooksStateResponse(findBooks.getUserbid(),
                findBooks.getBookstate(),
                findBooks.getSalestate());
    }

    @PostMapping("/books/{userbid}/delete")
    public BooksDeleteResponse booksDelete(@PathVariable("userbid") String userbid) {
        List<Journals> allJournalsByUserbid = journalsService.findAllJournalsByUserbid(userbid);

        for (Journals journals : allJournalsByUserbid) {
            journalsService.deleteJournals(journals);
        }

        Books findBooks = booksService.findOneBooks(userbid);
        booksService.deleteBooks(findBooks);

        return new BooksDeleteResponse("Delete Books Success");
    }

    @GetMapping("/books/{isbn}")
    public Result booksIsbnList(@PathVariable String isbn) {
        List<Profile> allProfileByIsbn = booksService.findAllProfileByIsbn(isbn);
        List<ProfileIsbnDto> collect = allProfileByIsbn.stream()
                .map(m -> new ProfileIsbnDto(m.getUid(), m.getNickname(), m.getUseriamgeUrl(), m.getUserimageName(), m.getUsermessage()))
                .collect(Collectors.toList());

        return new Result(collect);
    }
    @PutMapping("/books/salestateUpdate/{userbid}")
    public UpdateBooksStateResponse updateSaleState(@RequestBody UpdateBooksStateRequest request,
                                                     @PathVariable String userbid) {
        Books findBook = booksService.findOneBooks(userbid);
        booksService.updateBooks(userbid, findBook.getBookstate(), request.getSalestate());

        return new UpdateBooksStateResponse("bookstate update success");
    }
}
