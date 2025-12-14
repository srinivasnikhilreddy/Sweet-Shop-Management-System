package com.iss.dtos;

import lombok.*;
import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SweetDto
{
    private Long id;
    private String name;
    private String category;
    private BigDecimal price;
    private Integer quantity;
    private String imageUrl;
}
