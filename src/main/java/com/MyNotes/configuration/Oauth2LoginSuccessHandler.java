package com.MyNotes.configuration;

import com.MyNotes.dto.response.ApiResponse;
import com.MyNotes.dto.response.AuthenticationResponse;
import com.MyNotes.dto.request.UserCreateRequest;
import com.MyNotes.entity.Role;
import com.MyNotes.entity.User;
import com.MyNotes.exception.UserException;
import com.MyNotes.repository.UserRepository;
import com.MyNotes.service.JwtService;
import com.MyNotes.service.RoleService;
import com.MyNotes.service.UserService;
import com.MyNotes.util.TokenType;
import com.MyNotes.util.UserRole;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.Instant;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class Oauth2LoginSuccessHandler implements AuthenticationSuccessHandler {
    private final RoleService roleService;
    private final JwtService jwtService;
    private final UserService userService;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        Map<String, Object> userInfo = ((OAuth2User) authentication.getPrincipal()).getAttributes();
        String email = userInfo.get("email").toString();
        User user;

        if (userRepository.existsByEmail(email)) {
            user = userRepository.findByEmail(email).orElseThrow(() -> new UserException("User not found"));
        } else {
            String username = email.split("@")[0];
            String passwordEncode = passwordEncoder.encode(UUID.randomUUID().toString());

            UserCreateRequest userCreate = UserCreateRequest.builder()
                    .firstName(userInfo.get("given_name").toString())
                    .lastName(userInfo.get("name").toString())
                    .username(username)
                    .password(passwordEncode)
                    .email(userInfo.get("email").toString())
                    .build();

            Set<Role> roles = roleService.getRoles(UserRole.ROLE_USER);
            user = userService.createUser(userCreate, roles);
        }

        String accessToken = jwtService.generateToken(user, TokenType.ACCESS);
        String refreshToken = jwtService.generateToken(user, TokenType.REFRESH);

        AuthenticationResponse authResponse = AuthenticationResponse.builder()
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .username(user.getUsername())
                .email(user.getEmail())
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();

        ApiResponse<AuthenticationResponse> apiResponse = ApiResponse.<AuthenticationResponse>builder()
                .timestamp(Instant.now())
                .status(HttpStatus.OK.value())
                .success(true)
                .message("Login success")
                .data(authResponse)
                .path(request.getRequestURI())
                .build();

        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

        String responseJson = mapper.writeValueAsString(apiResponse);

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.setStatus(HttpStatus.OK.value());
        response.getWriter().write(responseJson);
    }
}
