package com.MyNotes.controller;

import com.MyNotes.dto.response.ApiResponse;
import com.MyNotes.dto.response.AuthenticationResponse;
import com.MyNotes.dto.request.AuthenticationRequest;
import com.MyNotes.dto.request.UserCreateRequest;
import com.MyNotes.dto.response.VocabularyResponse;
import com.MyNotes.entity.User;
import com.MyNotes.service.AuthenticationService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.Set;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {
    private final AuthenticationService authenticationService;

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResponse<Void> registerUser(
            @RequestBody UserCreateRequest userRequest,
            HttpServletRequest request) {
        authenticationService.registerUser(userRequest);

        return ApiResponse.<Void>builder()
                .timestamp(Instant.now())
                .status(HttpStatus.CREATED.value())
                .success(true)
                .message("Register success")
                .path(request.getRequestURI())
                .build();
    }

    @PostMapping("/login")
    @ResponseStatus(HttpStatus.OK)
    public ApiResponse<AuthenticationResponse> loginUser(
            @RequestBody AuthenticationRequest authRequest,
            HttpServletRequest request) {
        AuthenticationResponse response = authenticationService.authenticate(authRequest);

        return ApiResponse.<AuthenticationResponse>builder()
                .timestamp(Instant.now())
                .status(HttpStatus.OK.value())
                .success(true)
                .message("Login success")
                .data(response)
                .path(request.getRequestURI())
                .build();
    }
}
