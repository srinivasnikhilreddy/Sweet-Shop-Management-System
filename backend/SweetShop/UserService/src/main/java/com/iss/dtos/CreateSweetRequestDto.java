package com.iss.dtos;

import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateSweetRequestDto
{
    private String name;
    private String category;
    private BigDecimal price;
    private Integer quantity;
}
