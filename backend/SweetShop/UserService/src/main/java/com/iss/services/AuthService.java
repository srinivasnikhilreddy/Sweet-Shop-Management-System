package com.iss.services;

import com.iss.dtos.*;
import com.iss.models.AdminEntity;
import com.iss.models.RefreshTokenEntity;
import com.iss.models.RoleEntity;
import com.iss.models.UserEntity;
import com.iss.repositories.AdminRepository;
import com.iss.repositories.RoleRepository;
import com.iss.repositories.UserRepository;
import com.iss.securities.JwtUtil;
import com.iss.securities.RefreshTokenService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.*;

@Service
@RequiredArgsConstructor
public class AuthService
{
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final AdminRepository adminRepository;
    private final JwtUtil jwtUtil;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;
    private final RefreshTokenService refreshTokenService;

    @Value("${sweetshop.profile.upload.dir}")
    private String uploadDir;

    @Value("${sweetshop.profile.default.avatar:/profile-pictures/default.png}")
    private String defaultAvatar;

    private String roleName;

    @Transactional
    public UserProfileDto register(UserRegisterRequestDto userRegisterRequestDto, MultipartFile avatar) throws Exception
    {
        if(userRepository.findByUsername(userRegisterRequestDto.getUsername()).isPresent()){
            throw new IllegalArgumentException("Username already exists");
        }

        UserEntity userEntity = UserEntity.builder()
                .username(userRegisterRequestDto.getUsername())
                .passwordHash(passwordEncoder.encode(userRegisterRequestDto.getPassword()))
                .name(userRegisterRequestDto.getName())
                .phone(userRegisterRequestDto.getPhone())
                .enabled(true)
                .build();

        roleName = userRegisterRequestDto.getRole();
        if(roleName == null || roleName.isBlank()){
            roleName = "ROLE_USER";
        }

        RoleEntity role = roleRepository.findByName(roleName)
                        .orElseThrow(() -> new IllegalStateException(roleName + " not found"));

        userEntity.getRoles().add(role);

        if(avatar != null && !avatar.isEmpty()){
            userEntity.setAvatarUrl(saveAvatar(avatar));
        }else{
            userEntity.setAvatarUrl(defaultAvatar);
        }

        UserEntity saved = userRepository.save(userEntity);

        if("ROLE_ADMIN".equals(roleName)){
            AdminEntity admin = AdminEntity.builder()
                    .userEntity(saved)
                    .name(userRegisterRequestDto.getName())
                    .phone(userRegisterRequestDto.getPhone())
                    .department(userRegisterRequestDto.getDepartment())
                    .designation(userRegisterRequestDto.getDesignation())
                    .status(AdminEntity.Status.ACTIVE)
                    .build();

            adminRepository.save(admin);
        }

        String access = jwtUtil.generateAccessToken(
                saved.getUsername(), getRoleNames(saved)
        );
        RefreshTokenEntity refresh =
                refreshTokenService.createRefreshToken(saved.getUsername());

        return UserProfileDto.builder()
                .username(saved.getUsername())
                .roles(getRoleNames(saved))
                .accessToken(access)
                .refreshToken(refresh.getRefreshToken())
                .build();
    }

    public AuthResponseDto login(UserLoginRequestDto userLoginRequestDto)
    {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        userLoginRequestDto.getUsername(), userLoginRequestDto.getPassword()
                )
        );
        UserEntity user = userRepository.findByUsername(userLoginRequestDto.getUsername())
                .orElseThrow();
        String access = jwtUtil.generateAccessToken(
                user.getUsername(), getRoleNames(user)
        );
        RefreshTokenEntity refresh =
                refreshTokenService.createRefreshToken(user.getUsername());
        return AuthResponseDto.builder()
                .accessToken(access)
                .refreshToken(refresh.getRefreshToken())
                .expiresIn(3600L)
                .roles(getRoleNames(user))
                .build();
    }

    public AuthResponseDto refresh(RefreshTokenRequestDto refreshTokenRequestDto)
    {
        RefreshTokenEntity token =
                refreshTokenService.rotateRefreshToken(refreshTokenRequestDto.getRefreshToken())
                        .orElseThrow(() -> new IllegalArgumentException("Invalid refresh token"));

        UserEntity userEntity = userRepository.findByUsername(token.getEmail())
                .orElseThrow();

        return AuthResponseDto.builder()
                .accessToken(
                        jwtUtil.generateAccessToken(userEntity.getUsername(), getRoleNames(userEntity))
                )
                .refreshToken(token.getRefreshToken())
                .expiresIn(3600L)
                .roles(getRoleNames(userEntity))
                .build();
    }

    public void logout(String username)
    {
        refreshTokenService.deleteByEmail(username);
    }

    public UserProfileDto getProfile(String username)
    {
        UserEntity userEntity = userRepository.findByUsername(username).orElseThrow();
        return UserProfileDto.builder()
                .username(userEntity.getUsername())
                .roles(getRoleNames(userEntity))
                .build();
    }

    @Transactional
    public UserProfileDto updateProfile(String username, Map<String, Object> updates)
    {
        UserEntity userEntity = userRepository.findByUsername(username).orElseThrow();
        userEntity.setName((String) updates.getOrDefault("name", userEntity.getName()));
        userEntity.setPhone((String) updates.getOrDefault("phone", userEntity.getPhone()));
        return getProfile(username);
    }

    private String saveAvatar(MultipartFile avatar) throws Exception
    {
        String filename = UUID.randomUUID() + "_" +
                StringUtils.cleanPath(Objects.requireNonNull(avatar.getOriginalFilename()));
        File dir = new File(uploadDir);
        if(!dir.exists()) dir.mkdirs();
        avatar.transferTo(new File(dir, filename));
        return "/profile-pictures/" + filename;
    }

    private List<String> getRoleNames(UserEntity userEntity)
    {
        return userEntity.getRoles().stream().map(RoleEntity::getName).toList();
    }
}
