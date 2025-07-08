package com.MyNotes.controller;

import com.MyNotes.dto.request.VocabularyRequest;
import com.MyNotes.dto.response.ApiResponse;
import com.MyNotes.dto.response.VocabularyResponse;
import com.MyNotes.entity.User;
import com.MyNotes.service.VocabularyService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.List;

@RestController
@RequestMapping("/api/v1/vocabularies")
@RequiredArgsConstructor
public class VocabularyController {
    private final VocabularyService vocabularyService;

    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResponse<VocabularyResponse> createVocabulary(
            Authentication authentication,
            @RequestBody VocabularyRequest vocabularyRequest,
            HttpServletRequest request) {
        User user = (User) authentication.getPrincipal();

        VocabularyResponse response = vocabularyService.createVocabulary(user, vocabularyRequest);

        return ApiResponse.<VocabularyResponse>builder()
                .timestamp(Instant.now())
                .status(HttpStatus.CREATED.value())
                .success(true)
                .message("Created vocabulary")
                .data(response)
                .path(request.getRequestURI())
                .build();
    }

    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public ApiResponse<List<VocabularyResponse>> getVocabularies(
            Authentication authentication,
            HttpServletRequest request) {
        User user = (User) authentication.getPrincipal();

        List<VocabularyResponse> response = vocabularyService.getVocabularies(user);

        return ApiResponse.<List<VocabularyResponse>>builder()
                .timestamp(Instant.now())
                .status(HttpStatus.OK.value())
                .success(true)
                .message("Success")
                .data(response)
                .path(request.getRequestURI())
                .build();
    }
}
