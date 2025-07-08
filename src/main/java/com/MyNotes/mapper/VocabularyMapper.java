package com.MyNotes.mapper;

import com.MyNotes.dto.response.VocabularyResponse;
import com.MyNotes.entity.Vocabulary;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface VocabularyMapper {
    VocabularyResponse toDTO(Vocabulary vocabulary);
}
