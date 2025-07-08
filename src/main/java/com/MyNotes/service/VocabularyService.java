package com.MyNotes.service;

import com.MyNotes.dto.request.VocabularyRequest;
import com.MyNotes.dto.response.VocabularyResponse;
import com.MyNotes.entity.User;

import java.util.List;

public interface VocabularyService {
    VocabularyResponse createVocabulary(User user, VocabularyRequest vocabularyRequest);
    List<VocabularyResponse> getVocabularies(User user);
    void deleteVocabulary(User user,Long vocabularyId);
}
