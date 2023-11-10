package com.hallym.booker.service;

import com.hallym.booker.domain.LibraryList;
import com.hallym.booker.repository.LibraryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class LibraryService {
    private final LibraryRepository libraryRepository;
    public List<String> searchLibrary(String name){
        List<LibraryList> library = libraryRepository.findByName(name);
        List<String> libraryCode = new LinkedList<>();
        for(int i=0;i<library.size();i++){
            libraryCode.add(library.get(i).getLibCode());
        }
        return libraryCode;
    }
}
