package com.MyNotes.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@Builder
public class AuthenticationResponse {
    private String username;
    private String email;
    private String firstName;
    private String lastName;
    private String accessToken;
    private String refreshToken;
}