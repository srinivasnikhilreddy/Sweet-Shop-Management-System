package com.iss.dtos;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RestockRequestDto
{
    private Integer quantity;
}
