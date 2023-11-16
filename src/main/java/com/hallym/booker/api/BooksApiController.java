package com.hallym.booker.api;

import com.hallym.booker.domain.Books;
import com.hallym.booker.domain.Journals;
import com.hallym.booker.domain.Profile;
import com.hallym.booker.dto.Profile.ProfileDto;
import com.hallym.booker.dto.Result;
import com.hallym.booker.dto.books.*;
import com.hallym.booker.service.BooksService;
import com.hallym.booker.service.JournalsService;
import com.hallym.booker.service.LibraryService;
import com.hallym.booker.service.ProfileService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.persistence.EntityManager;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class BooksApiController {

    private final BooksService booksService;
    private final ProfileService profileService;
    private final JournalsService journalsService;
    private final LibraryService libraryService;

    @GetMapping("/bestseller")
    public Result aladinBestseller() {
        aladinApi aapi = new aladinApi();

        // 본인이 받은 api키를 추가
        String key = "";
        List<BestsellerResultDto> collect = new ArrayList<>();

        try {
            // parsing할 url 지정(API 키 포함해서)
            String url = "http://www.aladin.co.kr/ttb/api/ItemList.aspx?ttbkey=ttbwlco13232133003&QueryType=ItemNewAll&MaxResults=10&start=1&SearchTarget=Book&output=xml&Version=20131101";

            DocumentBuilderFactory dbFactoty = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactoty.newDocumentBuilder();
            Document doc = dBuilder.parse(url);

            // 제일 첫번째 태그
            doc.getDocumentElement().normalize();

            // 파싱할 tag
            NodeList nList = doc.getElementsByTagName("item");

            for (int temp = 0; temp < nList.getLength(); temp++) {
                Node nNode = nList.item(temp);

                Element eElement = (Element) nNode;

                BestsellerResultDto br = new BestsellerResultDto(aapi.getTagValue("title", eElement), aapi.getTagValue("author", eElement), aapi.getTagValue("isbn13", eElement), aapi.getTagValue("publisher", eElement),aapi.getTagValue("cover", eElement));
                collect.add(br);
            }

        } catch (Exception e) {
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

    /**
     * 읽고 있는 책 목록
     */
    @GetMapping("/booksList/{uid}")
    public Result booksList(@PathVariable("uid") String id) {
        aladinApi aapi = new aladinApi();

        List<Books> findBooks = booksService.findAllBooksByProfile(id);
        List<BooksWithImgDto> collect = findBooks.stream()
                .map(m -> new BooksWithImgDto(m.getUserbid(), id, m.getIsbn(), m.getBookstate(), m.getSalestate()))
                .collect(Collectors.toList());

        // 본인이 받은 api키를 추가
        String key = "";
        try {
            DocumentBuilderFactory dbFactoty = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactoty.newDocumentBuilder();
            for(int i=0;i<collect.size();i++) {
                // parsing할 url 지정(API 키 포함해서)
                String url = "https://www.aladin.co.kr/ttb/api/ItemLookUp.aspx?ttbkey=ttbwlco13232133003&itemIdType=ISBN13&SearchTarget=Book&ItemId=" + collect.get(i).getIsbn() + "&output=xml&Version=20131101";

                Document doc = dBuilder.parse(url);

                // 제일 첫번째 태그
                doc.getDocumentElement().normalize();

                // 파싱할 tag
                NodeList nList = doc.getElementsByTagName("item");

                Node nNode = nList.item(0);
                Element eElement = (Element) nNode;
                String br1 = (aapi.getTagValue("cover", eElement));
                collect.get(i).setCover(br1);
                String br2 = (aapi.getTagValue("author", eElement));
                collect.get(i).setAuthor(br2);
                String br3 = (aapi.getTagValue("title", eElement));
                collect.get(i).setTitle(br3);
                String br4 = (aapi.getTagValue("publisher", eElement));
                collect.get(i).setPublisher(br4);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        for(int i=0;i<collect.size();i++){
            String isbn = collect.get(i).getIsbn();
            List<ProfileIsbnDto> list= booksIsbnList(isbn, id);
            collect.get(i).setProfile(list);
        }
        return new Result(collect);
    }
    //나와 같이 읽는 사람
    private List<ProfileIsbnDto> booksIsbnList(String isbn, String id) {
        List<Profile> allProfileByIsbn = booksService.findAllProfileByIsbn(isbn);
        for(int i=0;i<allProfileByIsbn.size();i++){
            if((allProfileByIsbn.get(i).getUid()).equals(id)){
                allProfileByIsbn.remove(i);
            }
        }
        List<ProfileIsbnDto> collect = allProfileByIsbn.stream()
                .map(m -> new ProfileIsbnDto(m.getUid(), m.getNickname(), m.getUseriamgeUrl(), m.getUserimageName(), m.getUsermessage()))
                .collect(Collectors.toList());

        return collect;
    }

    /**
     * 한 책에 대한 독서 상태 조회
     */
    @GetMapping("/booksState/uid={uid}&isbn={isbn}")
    public BooksStateResponse booksState(@PathVariable("uid") String uid,@PathVariable("isbn") String isbn) {
        Books findBooks = booksService.searchBooksByIsbn(uid,isbn);
        if(findBooks == null) return null;
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



    @PutMapping("/books/salestateUpdate/{userbid}")
    public UpdateBooksStateResponse updateSaleState(@RequestBody UpdateBooksStateRequest request,
                                                    @PathVariable String userbid) {
        Books findBook = booksService.findOneBooks(userbid);
        booksService.updateBooks(userbid, findBook.getBookstate(), request.getSalestate());

        return new UpdateBooksStateResponse("bookstate update success");
    }

    /**
     * 책 검색에서 isbn을 통해 독서 상태 조회
     */
    @GetMapping("books/search/uid={uid}&searchOne={searchOne}")
    public Result booksSearch(@PathVariable("uid") String uid, @PathVariable("searchOne") String searchOne) {
        aladinApi aapi = new aladinApi();

        // 본인이 받은 api키를 추가
        String key = "";
        List<BooksWithStateDto> collect = new ArrayList<>();

        try {
            // parsing할 url 지정(API 키 포함해서)
            String url = "http://www.aladin.co.kr/ttb/api/ItemSearch.aspx?ttbkey=ttbwlco13232133003&Sort=SalesPoint&Query=" + searchOne + "&QueryType=Keyword&start=1&SearchTarget=Book&output=xml&Version=20131101";

            DocumentBuilderFactory dbFactoty = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactoty.newDocumentBuilder();
            Document doc = dBuilder.parse(url);

            // 제일 첫번째 태그
            doc.getDocumentElement().normalize();

            // 파싱할 tag
            NodeList nList = doc.getElementsByTagName("item");

            for (int temp = 0; temp < nList.getLength(); temp++) {
                Node nNode = nList.item(temp);

                Element eElement = (Element) nNode;

                String BookName = aapi.getTagValue("title", eElement);
                String BookAuthor = aapi.getTagValue("author", eElement);
                String isbn = aapi.getTagValue("isbn13", eElement);
                String publisher = aapi.getTagValue("publisher", eElement);
                String cover = aapi.getTagValue("cover", eElement);

                BooksWithStateDto booksWithStateDto = new BooksWithStateDto(BookName,BookAuthor,isbn,publisher,cover);
                collect.add(booksWithStateDto);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        //collect에 isbn들 넣어줌
        //b에 isbn으로 찾은 책값 있는지 확인
        List<BooksWithStateDto> dtos = new LinkedList<>();
        for (int i = 0; i < collect.size(); i++) {
            Books b = booksService.searchBooksByIsbn(uid, collect.get(i).getIsbn());
            if (b != null) {
                collect.get(i).setUid(uid);
                collect.get(i).setBookstate(b.getBookstate());
                dtos.add(collect.get(i));
            } else {
                collect.get(i).setUid(uid);
                collect.get(i).setBookstate(0);
                dtos.add(collect.get(i));

            }
        }
        return new Result(dtos);

    }

    /**
     * 책교환페이지에서 도서 검색
     */
    @GetMapping("/books/sale/searchOne/{searchOne}")
    public Result booksFind(@PathVariable("searchOne") String searchOne) {
        aladinApi aapi = new aladinApi();

        // 본인이 받은 api키를 추가
        String key = "";
        List<BooksFindDto> collect = new ArrayList<>();

        try {
            // parsing할 url 지정(API 키 포함해서)
            String url = "http://www.aladin.co.kr/ttb/api/ItemSearch.aspx?ttbkey=ttbwlco13232133003&SearchTarget=Book&QueryType=Keyword&Sort=SalesPoint&Query=" + searchOne;

            DocumentBuilderFactory dbFactoty = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactoty.newDocumentBuilder();
            Document doc = dBuilder.parse(url);

            // 제일 첫번째 태그
            doc.getDocumentElement().normalize();

            // 파싱할 tag
            NodeList nList = doc.getElementsByTagName("item");

            for (int temp = 0; temp < nList.getLength(); temp++) {
                Node nNode = nList.item(temp);

                Element eElement = (Element) nNode;

                BooksFindDto br = new BooksFindDto(aapi.getTagValue("title", eElement), aapi.getTagValue("author", eElement), aapi.getTagValue("isbn13", eElement), aapi.getTagValue("publisher", eElement));
                collect.add(br);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return new Result(collect);
    }

    /**
     * 책교환에서 isbn과 salesstate을 이용하여 uid 추출 후 profile 조회
     */
    @GetMapping("books/sale/isbn/{isbn}")
    public Result profileSearch(@PathVariable("isbn") String isbn) {
        List<Profile> profile = booksService.findByIsbnAndSalesstate(isbn);
        List<ProfileDto> profileDtoList = new LinkedList<>();
        for (int i = 0; i < profile.size(); i++) {
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
     * 지역 정보들과 isbn을 받아 책 소유하고 있는 도서관 리스트 조회
     */
    @GetMapping("books/sale/library/region={region}&dtl_region={dtl_region}&isbn={isbn}")
    public Result librarySearchWithBook(@PathVariable("region") String region, @PathVariable("dtl_region") String dtl_region, @PathVariable("isbn") String isbn) {
        aladinApi aapi = new aladinApi();

        // 본인이 받은 api키를 추가
        String key = "";
        List<SaleLibraryDto> collect = new ArrayList<>();

        try {
            // parsing할 url 지정(API 키 포함해서)
            String url = "https://data4library.kr/api/libSrchByBook?authKey=586d5917c4e4a05c24620be8d9516fe0c1fea104933af91d92a867d4d8fccdca&isbn=" + isbn + "&region=" + region + "&dtl_region=" + dtl_region;
            DocumentBuilderFactory dbFactoty = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactoty.newDocumentBuilder();
            Document doc = dBuilder.parse(url);

            // 제일 첫번째 태그
            doc.getDocumentElement().normalize();

            // 파싱할 tag
            NodeList nList = doc.getElementsByTagName("lib");

            for (int temp = 0; temp < nList.getLength(); temp++) {
                Node nNode = nList.item(temp);

                Element eElement = (Element) nNode;

                SaleLibraryDto br = new SaleLibraryDto(aapi.getTagValue("libName", eElement), aapi.getTagValue("address", eElement), aapi.getTagValue("tel", eElement), aapi.getTagValue("latitude", eElement), aapi.getTagValue("longitude", eElement), aapi.getTagValue("homepage", eElement), aapi.getTagValue("closed", eElement), aapi.getTagValue("operatingTime", eElement));
                collect.add(br);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }


        return new Result(collect);
    }

    /**
     * 도서관 검색
     */
    @GetMapping("books/sale/library/region={region}&dtl_region={dtl_region}&searchOne={searchOne}")
    public Result librarySearch(@PathVariable("region") String region, @PathVariable("dtl_region") String dtl_region, @PathVariable("searchOne") String searchOne) {

        // 본인이 받은 api키를 추가
        String key = "586d5917c4e4a05c24620be8d9516fe0c1fea104933af91d92a867d4d8fccdca";
        List<SaleLibraryDto> collect = new ArrayList<>();

        String url;
        if (!region.equals("") && !dtl_region.equals("") && !searchOne.equals("")) { //region,dtl_region,searchOne을 다 입력했을 경우
            List<String> libraryCode = libraryService.searchLibrary(searchOne);
            for (int i = 0; i < libraryCode.size(); i++) {
                url = "http://data4library.kr/api/libSrch?authKey=" + key + "&region=" + region + "&dtl_region=" + dtl_region + "&libCode=" + libraryCode.get(i);
                List<SaleLibraryDto> l = searchLibraries(url); //임시로 해당 url로 검색한 결과 넣기
                for (int j = 0; j < l.size(); j++) {
                    collect.add(l.get(j)); //최종 리스트에 추가
                    System.out.println(l.get(j));
                }
            }
            List<SaleLibraryDto> r = DeduplicationUtils.deduplication(collect,SaleLibraryDto::getLibName); //중복 제거 코드
            return new Result(r);
        } else if (!region.equals("") && !searchOne.equals("")) { //dtl_Region 빼고 다 입력했을 경우
            List<String> libraryCode = libraryService.searchLibrary(searchOne);
            for (int i = 0; i < libraryCode.size(); i++) {
                url = "http://data4library.kr/api/libSrch?authKey=" + key + "&region=" + region + "&libCode=" + libraryCode.get(i);
                List<SaleLibraryDto> l = searchLibraries(url); //임시로 해당 url로 검색한 결과 넣기
                for (int j = 0; j < l.size(); j++) {
                    collect.add(l.get(j)); //최종 리스트에 추가
                }
            }
            List<SaleLibraryDto> r = DeduplicationUtils.deduplication(collect,SaleLibraryDto::getLibName); //중복 제거 코드
            return new Result(r);
        } else if (region.equals("") && !searchOne.equals("")) { //searchOne만 입력했을 경우
            List<String> libraryCode = libraryService.searchLibrary(searchOne);
            for (int i = 0; i < libraryCode.size(); i++) {
                url = "http://data4library.kr/api/libSrch?authKey=" + key + "&libCode=" + libraryCode.get(i);
                List<SaleLibraryDto> l = searchLibraries(url); //임시로 해당 url로 검색한 결과 넣기
                for (int j = 0; j < l.size(); j++) {
                    collect.add(l.get(j)); //최종 리스트에 추가
                }
            }
            return new Result(collect);
        } else if (!region.equals("") && dtl_region.equals("") && searchOne.equals("")) { //region만 입력했을 경우
            url = "http://data4library.kr/api/libSrch?authKey=" + key + "&region=" + region;
            collect = searchLibraries(url); //임시로 해당 url로 검색한 결과 넣기
            return new Result(collect);
        } else if (!region.equals("") && !dtl_region.equals("") && searchOne.equals("")) { //region과 dtl_region을 입력했을 경우
            url = "http://data4library.kr/api/libSrch?authKey=" + key + "&region=" + region + "&dtl_region=" + dtl_region;
            collect = searchLibraries(url); //임시로 해당 url로 검색한 결과 넣기
            return new Result(collect);
        } else {
            return null;
        }
    }

    private List<SaleLibraryDto> searchLibraries(String url) {
        aladinApi aapi = new aladinApi();
        List<SaleLibraryDto> collect = new ArrayList<>();
        try {
            DocumentBuilderFactory dbFactoty = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactoty.newDocumentBuilder();
            Document doc = dBuilder.parse(url);

            // 제일 첫번째 태그
            doc.getDocumentElement().normalize();

            // 파싱할 tag
            NodeList nList = doc.getElementsByTagName("lib");

            for (int temp = 0; temp < nList.getLength(); temp++) {
                Node nNode = nList.item(temp);

                Element eElement = (Element) nNode;

                SaleLibraryDto br = new SaleLibraryDto(aapi.getTagValue("libName", eElement), aapi.getTagValue("address", eElement), aapi.getTagValue("tel", eElement), aapi.getTagValue("latitude", eElement), aapi.getTagValue("longitude", eElement), aapi.getTagValue("homepage", eElement), aapi.getTagValue("closed", eElement), aapi.getTagValue("operatingTime", eElement));
                collect.add(br);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }


        return collect;
    }

    /**
     * 책 상세 정보
     */
    @GetMapping("books/booksDetail/{isbn}")
    public Result booksDetail(@PathVariable("isbn") String isbn) {
        aladinApi aapi = new aladinApi();

        // 본인이 받은 api키를 추가
        String key = "";
        BooksDetailDto collect = new BooksDetailDto();

        try {
            // parsing할 url 지정(API 키 포함해서)
            String url = "http://www.aladin.co.kr/ttb/api/ItemLookUp.aspx?ttbkey=ttbwlco13232133003&itemIdType=ISBN13&ItemId="+isbn+"&output=xml&Version=20131101&O";

            DocumentBuilderFactory dbFactoty = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactoty.newDocumentBuilder();
            Document doc = dBuilder.parse(url);

            // 제일 첫번째 태그
            doc.getDocumentElement().normalize();

            // 파싱할 tag
            NodeList nList = doc.getElementsByTagName("item");

            for (int temp = 0; temp < nList.getLength(); temp++) {
                Node nNode = nList.item(temp);

                Element eElement = (Element) nNode;

                collect = new BooksDetailDto(aapi.getTagValue("title", eElement), aapi.getTagValue("author", eElement), aapi.getTagValue("pubDate", eElement),aapi.getTagValue("description", eElement),aapi.getTagValue("isbn13", eElement), aapi.getTagValue("cover", eElement), aapi.getTagValue("categoryName", eElement), aapi.getTagValue("publisher", eElement));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return new Result(collect);
    }
}