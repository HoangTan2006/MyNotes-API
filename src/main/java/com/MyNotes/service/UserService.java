package com.MyNotes.service;

import com.MyNotes.dto.request.UserCreateRequest;
import com.MyNotes.entity.Role;
import com.MyNotes.entity.User;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.Set;

public interface UserService extends UserDetailsService {
    User createUser(UserCreateRequest userRequest, Set<Role> roles);
}
