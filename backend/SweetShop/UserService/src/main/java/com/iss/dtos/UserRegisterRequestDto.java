package com.iss.dtos;

import com.iss.models.UserEntity;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserRegisterRequestDto
{
    private String username;
    private String password;
    private String name;
    private String phone;
    private String role;

    //Admin only fields
    private String department;
    private String designation;
}
