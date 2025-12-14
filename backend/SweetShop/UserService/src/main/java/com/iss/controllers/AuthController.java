package com.iss.controllers;

import com.iss.dtos.*;
import com.iss.services.AuthService;
import com.iss.securities.JwtUtil;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController
{
    private final AuthService authService;
    private final JwtUtil jwtUtil;

    /*POST http://localhost:9070/api/auth/register
     - Key: user (Text) -> JSON:
       {
        "username":"john@example.com",
        "password":"Pass@123",
        "name":"John Doe",
        "phone":"9876543210",
        "role":"ROLE_USER" // ROLE_ADMIN
        "department": "Product-Management", //for Admin only
        "designation": "Product Manager" //for Admin only
       }
     - Key: avatar (File) optional -> profile image*/
    @PostMapping("/register")
    public ResponseEntity<?> register(
            @RequestPart("user") UserRegisterRequestDto userRegisterRequestDto,
            @RequestPart(value = "avatar", required = false) MultipartFile avatar)
    {
        try{
            UserProfileDto userProfileDto = authService.register(userRegisterRequestDto, avatar);
            return ResponseEntity.status(HttpStatus.CREATED).body(userProfileDto);
        }catch(Exception ex){
            return ResponseEntity.badRequest()
                    .body(Map.of("error", "Registration failed: " + ex.getMessage()));
        }
    }

    /*POST http://localhost:9070/api/auth/login
      Body:
      {
        "username": "john@example.com",
        "password": "Pass@123"
      }*/
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody UserLoginRequestDto userLoginRequestDto)
    {
        try{
            AuthResponseDto authResponseDto = authService.login(userLoginRequestDto);
            return ResponseEntity.ok(authResponseDto);
        }catch(Exception ex){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("error", "Invalid username or password"));
        }
    }

    /*POST http://localhost:9070/api/auth/refresh
      Body:
      {
        "refreshToken": "uuid-or-token"
      }*/
    @PostMapping("/refresh")
    public ResponseEntity<?> refresh(@RequestBody RefreshTokenRequestDto refreshTokenRequestDto)
    {
        try{
            AuthResponseDto authResponseDto = authService.refresh(refreshTokenRequestDto);
            return ResponseEntity.ok(authResponseDto);
        }catch(Exception ex){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("error", ex.getMessage()));
        }
    }

    /*POST http://localhost:9070/api/auth/logout
      Authorization: Bearer <access token>*/
    @PostMapping("/logout")
    public ResponseEntity<?> logout(HttpServletRequest httpServletRequest)
    {
        try{
            String username = extractUsernameFromRequest(httpServletRequest);
            authService.logout(username);
            return ResponseEntity.ok(Map.of("message", "Logged out"));
        }catch(Exception ex){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("error", ex.getMessage()));
        }
    }

    /*POST http://localhost:9070/api/auth/getProfile
      Authorization: Bearer <access token>*/
    @GetMapping("/getProfile")
    public ResponseEntity<?> getProfile(HttpServletRequest httpServletRequest)
    {
        try{
            String username = extractUsernameFromRequest(httpServletRequest);
            UserProfileDto userProfileDto = authService.getProfile(username);
            return ResponseEntity.ok(userProfileDto);
        }catch(Exception ex){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("error", ex.getMessage()));
        }
    }

    /*POST http://localhost:9070/api/auth/logout
    Authorization: Bearer <access token>
     Body:
     {
       "name":"John Doe",
       "phone":"9998887776"
     }*/
    @PutMapping("/updateProfile")
    public ResponseEntity<?> updateProfile(@RequestBody Map<String, Object> body, HttpServletRequest httpServletRequest)
    {
        try{
            String username = extractUsernameFromRequest(httpServletRequest);
            UserProfileDto userProfileDto = authService.updateProfile(username, body);
            return ResponseEntity.ok(userProfileDto);
        }catch(Exception ex){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("error", ex.getMessage()));
        }
    }

    private String extractUsernameFromRequest(HttpServletRequest httpServletRequest)
    {
        String header = httpServletRequest.getHeader("Authorization");
        if(header == null || !header.startsWith("Bearer ")){
            throw new IllegalArgumentException("Missing or invalid Authorization header");
        }
        String token = header.substring(7).trim();
        //System.out.println(token);
        if(token.isBlank()){
            throw new IllegalArgumentException("Empty bearer token");
        }
        return jwtUtil.validateTokenAndRetrieveSubject(token);
    }
}
