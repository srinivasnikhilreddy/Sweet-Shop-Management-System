package com.iss.dtos;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AuthResponseDto
{
    private String accessToken;
    private String refreshToken;
    private Long expiresIn;
    private List<String> roles;
}
