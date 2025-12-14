package com.iss.dtos;

import lombok.*;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserProfileDto
{
    private String username;
    private List<String> roles;
    private String accessToken;
    private String refreshToken;
}
