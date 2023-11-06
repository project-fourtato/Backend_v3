package com.hallym.booker.dto.journals;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class CreateMemberRequest {
    private String ptitle;
    private String pcontents;
    private String pimageUrl;
    private String pimageName;

    public CreateMemberRequest() {}
}
