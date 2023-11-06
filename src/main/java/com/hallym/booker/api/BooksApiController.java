package com.hallym.booker.api;

import com.hallym.booker.domain.Books;
import com.hallym.booker.domain.Profile;
import com.hallym.booker.dto.Profile.ProfileDto;
import com.hallym.booker.dto.Result;
import com.hallym.booker.dto.books.*;
import com.hallym.booker.service.BooksService;
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
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class BooksApiController {

    private final BooksService booksService;
    private final ProfileService profileService;

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

    @PostMapping("/search/Journals/new/{uid}")
    public CreateBooksResponse createBooks(
            @PathVariable("uid") String uid,
            @RequestBody CreateBooksRequest request) {
        Profile findProfile = profileService.findOne(uid);

        //String userbid, String isbn, int bookstate, int salestate
        Books books = new Books(request.getUserbid(), request.getIsbn(), request.getBookstate(), request.getSalestate());
        books.setProfile(findProfile); //여기서 이렇게 해주는게 맞는감,, -> 생성 메소드..?
        String userbid = booksService.saveBooks(books);

        return new CreateBooksResponse(userbid);
    }

    @GetMapping("/booksList/{uid}")
    public Result booksList(@PathVariable("uid") String id) {
        List<Books> findBooks = booksService.findAllBooksByProfile(id);
        List<BooksDto> collect = findBooks.stream()
                .map(m -> new BooksDto(m.getUserbid(), id, m.getIsbn(), m.getBookstate(), m.getSalestate()))
                .collect(Collectors.toList());

        return new Result(collect);
    }

    @GetMapping("/books/{userbid}")
    public BooksStateResponse booksState(@PathVariable("userbid") String userbid) {
        Books findBooks = booksService.findOneBooks(userbid);

        return new BooksStateResponse(findBooks.getUserbid(),
                findBooks.getBookstate(),
                findBooks.getSalestate());
    }

    @GetMapping("books/search/uid={uid}&searchOne={searchOne}")
    public Result booksSearch(@PathVariable("uid") String uid, @PathVariable("searchOne")String searchOne){
        aladinApi aapi = new aladinApi();

        // 본인이 받은 api키를 추가
        String key = "";
        List<String> collect = new ArrayList<>();

        try{
            // parsing할 url 지정(API 키 포함해서)
            String url = "http://www.aladin.co.kr/ttb/api/ItemSearch.aspx?ttbkey=ttbwlco13232133003&Query="+searchOne+"&QueryType=Keyword&start=1&SearchTarget=Book&output=xml&Version=20131101";

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

                String isbn = aapi.getTagValue("isbn13", eElement);
                collect.add(isbn);
            }

        } catch (Exception e){
            e.printStackTrace();
        }
        //collect에 isbn들 넣어줌
        //b에 isbn으로 찾은 책값 있는지 확인
        List<BooksSearchDto> booksSearchDtos = new LinkedList<>();
        for(int i=0;i<collect.size();i++){
            Books b = booksService.searchBooksByIsbn(uid, collect.get(i));
            if(b!=null){
                BooksSearchDto booksSearchDto = new BooksSearchDto(uid,b.getIsbn(),b.getBookstate());
                booksSearchDtos.add(booksSearchDto);
            }
            else{
                BooksSearchDto booksSearchDto = new BooksSearchDto(uid, collect.get(i), 0);
                booksSearchDtos.add(booksSearchDto);
            }
        }

        return new Result(booksSearchDtos);

    }
    /**
     * 책교환페이지에서 도서 검색
     */
    @GetMapping("/books/sale/searchOne/{searchOne}")
    public Result booksFind(@PathVariable("searchOne")String searchOne){
        aladinApi aapi = new aladinApi();

        // 본인이 받은 api키를 추가
        String key = "";
        List<BooksFindDto> collect = new ArrayList<>();

        try{
            // parsing할 url 지정(API 키 포함해서)
            String url = "http://www.aladin.co.kr/ttb/api/ItemSearch.aspx?ttbkey=ttbwlco13232133003&Query="+searchOne+"&QueryType=Keyword&start=1&SearchTarget=Book&output=xml&Version=20131101";

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

                BooksFindDto br = new BooksFindDto(aapi.getTagValue("title", eElement), aapi.getTagValue("author", eElement), aapi.getTagValue("isbn13", eElement), aapi.getTagValue("publisher",eElement));
                collect.add(br);
            }

        } catch (Exception e){
            e.printStackTrace();
        }


        return new Result(collect);
    }

    /**
     * 책교환에서 isbn과 salesstate을 이용하여 uid 추출 후 profile 조회
     */
    @GetMapping("books/sale/isbn/{isbn}")
    public Result profileSearch(@PathVariable("isbn") String isbn){
        List<Profile> profile= booksService.findByIsbnAndSalesstate(isbn);
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
}
