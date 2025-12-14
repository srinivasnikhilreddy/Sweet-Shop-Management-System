package com.iss.services;

import com.iss.dtos.UserLoginRequestDto;
import com.iss.dtos.UserRegisterRequestDto;
import com.iss.models.RefreshTokenEntity;
import com.iss.models.RoleEntity;
import com.iss.models.UserEntity;
import com.iss.repositories.RoleRepository;
import com.iss.repositories.UserRepository;
import com.iss.securities.JwtUtil;
import com.iss.securities.RefreshTokenService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AuthServiceTest
{
    @Mock
    private UserRepository userRepository;

    @Mock
    private RoleRepository roleRepository;

    @Mock
    private JwtUtil jwtUtil;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private RefreshTokenService refreshTokenService;

    @InjectMocks
    private AuthService authService;

    @Test
    void shouldRegisterUserSuccessfully() throws Exception
    {
        //Arrange
        RoleEntity roleEntity = RoleEntity.builder()
                .name("ROLE_USER")
                .build();

        when(userRepository.findByUsername("user@mail.com"))
                .thenReturn(Optional.empty());

        when(roleRepository.findByName("ROLE_USER"))
                .thenReturn(Optional.of(roleEntity));

        when(passwordEncoder.encode("pass"))
                .thenReturn("hashed");

        when(jwtUtil.generateAccessToken(anyString(), anyList()))
                .thenReturn("access-token");

        RefreshTokenEntity refreshToken = new RefreshTokenEntity();
        refreshToken.setRefreshToken("refresh-token");

        when(refreshTokenService.createRefreshToken("user@mail.com"))
                .thenReturn(refreshToken);

        when(userRepository.save(any(UserEntity.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        UserRegisterRequestDto userRegisterRequestDto = UserRegisterRequestDto.builder()
                .username("user@mail.com")
                .password("pass")
                .name("User")
                .phone("9999999999")
                .build();

        //Act + Assert
        assertDoesNotThrow(() -> authService.register(userRegisterRequestDto, null));
    }

    @Test
    void shouldLoginSuccessfully()
    {
        UserEntity userEntity = UserEntity.builder()
                .username("test@mail.com")
                .passwordHash("hashed")
                .roles(Set.of(RoleEntity.builder().name("ROLE_USER").build()))
                .build();

        when(userRepository.findByUsername("test@mail.com"))
                .thenReturn(Optional.of(userEntity));

        when(jwtUtil.generateAccessToken(anyString(), anyList()))
                .thenReturn("access-token");

        RefreshTokenEntity refreshToken = new RefreshTokenEntity();
        refreshToken.setRefreshToken("refresh-token");

        when(refreshTokenService.createRefreshToken("test@mail.com"))
                .thenReturn(refreshToken);

        assertDoesNotThrow(() ->
                authService.login(new UserLoginRequestDto("test@mail.com", "pass"))
        );
    }
}

