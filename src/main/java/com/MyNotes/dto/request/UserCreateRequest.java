package com.MyNotes.dto.request;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class UserCreateRequest {
    private String firstName;
    private String lastName;
    private String username;
    private String password;
    private String email;
}
