package com.MyNotes.dto.response;

import lombok.*;

@Setter
@Getter
@Builder
public class VocabularyResponse {
    private Long id;
    private String word;
    private String note;
}
