package com.MyNotes.service;

import com.MyNotes.dto.response.AuthenticationResponse;
import com.MyNotes.dto.request.AuthenticationRequest;
import com.MyNotes.dto.request.UserCreateRequest;

public interface AuthenticationService {
    AuthenticationResponse authenticate(AuthenticationRequest authenticationRequest);
    void registerUser(UserCreateRequest userRequest);
}
