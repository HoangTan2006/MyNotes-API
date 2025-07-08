package com.MyNotes.service.impl;

import com.MyNotes.dto.request.UserCreateRequest;
import com.MyNotes.entity.Role;
import com.MyNotes.entity.User;
import com.MyNotes.exception.UserException;
import com.MyNotes.repository.UserRepository;
import com.MyNotes.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username).orElseThrow(() -> new UserException("User not found"));
    }

    @Override
    public User createUser(UserCreateRequest userRequest, Set<Role> roles) {
        User user = User.builder()
                .firstName(userRequest.getFirstName())
                .lastName(userRequest.getLastName())
                .username(userRequest.getUsername())
                .password(userRequest.getPassword())
                .email(userRequest.getEmail())
                .roles(roles)
                .isLock(false)
                .build();

        return userRepository.save(user);
    }
}
