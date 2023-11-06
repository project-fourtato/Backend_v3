package com.hallym.booker.dto.journals;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UpdateJournalsRequest {
    private String jid;
    private String ptitle;
    private String pcontents;
    private String pimageUrl;
    private String pimageName;

    public UpdateJournalsRequest() {}
}
