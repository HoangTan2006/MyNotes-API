package com.MyNotes.service.impl;

import com.MyNotes.dto.response.AuthenticationResponse;
import com.MyNotes.dto.request.AuthenticationRequest;
import com.MyNotes.dto.request.UserCreateRequest;
import com.MyNotes.entity.Role;
import com.MyNotes.entity.User;
import com.MyNotes.exception.UserException;
import com.MyNotes.repository.UserRepository;
import com.MyNotes.service.AuthenticationService;
import com.MyNotes.service.JwtService;
import com.MyNotes.service.RoleService;
import com.MyNotes.service.UserService;
import com.MyNotes.util.TokenType;
import com.MyNotes.util.UserRole;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {
    private final UserRepository userRepository;
    private final RoleService roleService;
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    @Override
    public AuthenticationResponse authenticate(AuthenticationRequest authRequest) {
        try {
            User user = (User) authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            authRequest.getUsername(),
                            authRequest.getPassword())).getPrincipal();

            String accessToken = jwtService.generateToken(user, TokenType.ACCESS);
            String refreshToken = jwtService.generateToken(user, TokenType.REFRESH);

            return AuthenticationResponse.builder()
                    .firstName(user.getFirstName())
                    .lastName(user.getLastName())
                    .username(user.getUsername())
                    .email(user.getEmail())
                    .accessToken(accessToken)
                    .refreshToken(refreshToken)
                    .build();
        } catch (Exception e) {
            throw new AuthenticationException("Username or password invalid") {
            };
        }
    }

    @Override
    public void registerUser(UserCreateRequest userRequest) {
        if (userRepository.existsByUsername(userRequest.getUsername())) throw new UserException("Username is already exists");
        if (userRepository.existsByEmail(userRequest.getEmail())) throw new UserException("Email is already exists");

        Set<Role> roles = roleService.getRoles(UserRole.ROLE_USER);

        String passwordEncode = passwordEncoder.encode(userRequest.getPassword());
        userRequest.setPassword(passwordEncode);

        userService.createUser(userRequest, roles);
    }
}
