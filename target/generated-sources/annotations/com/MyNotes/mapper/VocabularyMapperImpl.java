package com.MyNotes.mapper;

import com.MyNotes.dto.response.VocabularyResponse;
import com.MyNotes.entity.Vocabulary;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-07-09T00:48:55+0700",
    comments = "version: 1.6.3, compiler: javac, environment: Java 21.0.6 (Oracle Corporation)"
)
@Component
public class VocabularyMapperImpl implements VocabularyMapper {

    @Override
    public VocabularyResponse toDTO(Vocabulary vocabulary) {
        if ( vocabulary == null ) {
            return null;
        }

        VocabularyResponse.VocabularyResponseBuilder vocabularyResponse = VocabularyResponse.builder();

        vocabularyResponse.id( vocabulary.getId() );
        vocabularyResponse.word( vocabulary.getWord() );
        vocabularyResponse.note( vocabulary.getNote() );

        return vocabularyResponse.build();
    }
}
