package com.MyNotes.dto.request;

import lombok.Getter;

@Getter
public class AuthenticationRequest {
    private String username;
    private String password;
}
