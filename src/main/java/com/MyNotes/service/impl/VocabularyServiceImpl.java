package com.MyNotes.service.impl;

import com.MyNotes.dto.request.VocabularyRequest;
import com.MyNotes.dto.response.VocabularyResponse;
import com.MyNotes.entity.User;
import com.MyNotes.entity.Vocabulary;
import com.MyNotes.mapper.VocabularyMapper;
import com.MyNotes.repository.VocabularyRepository;
import com.MyNotes.service.VocabularyService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class VocabularyServiceImpl implements VocabularyService {
    private final VocabularyRepository vocabularyRepository;
    private final VocabularyMapper vocabularyMapper;

    @Override
    public VocabularyResponse createVocabulary(User user, VocabularyRequest vocabularyRequest) {
        Vocabulary vocabulary = Vocabulary.builder()
                .word(vocabularyRequest.getWord())
                .note(vocabularyRequest.getNote())
                .user(user)
                .build();

        return vocabularyMapper.toDTO(
                vocabularyRepository.save(vocabulary));
    }

    @Override
    public List<VocabularyResponse> getVocabularies(User user) {
        List<Vocabulary> vocabularies = vocabularyRepository.findAllByUser(user);

        return vocabularies
                .stream()
                .map(vocabularyMapper::toDTO)
                .toList();
    }
}
