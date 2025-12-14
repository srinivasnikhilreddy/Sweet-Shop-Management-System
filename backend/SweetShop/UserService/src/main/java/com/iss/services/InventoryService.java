package com.iss.services;

import com.iss.dtos.SweetDto;
import com.iss.models.SweetEntity;
import com.iss.repositories.SweetRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Transactional
public class InventoryService
{
    private final SweetRepository sweetRepository;
    public SweetDto purchaseSweet(Long id, int quantity)
    {
        SweetEntity sweetEntity = sweetRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Sweet not found"));

        if(sweetEntity.getQuantity() < quantity){
            throw new RuntimeException("Insufficient stock");
        }
        sweetEntity.setQuantity(sweetEntity.getQuantity() - quantity);
        SweetEntity saved = sweetRepository.save(sweetEntity);
        return mapToDto(saved);
    }

    public SweetDto restockSweet(Long id, int quantity)
    {
        SweetEntity sweetEntity = sweetRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Sweet not found"));
        sweetEntity.setQuantity(sweetEntity.getQuantity() + quantity);
        SweetEntity saved = sweetRepository.save(sweetEntity);
        return mapToDto(saved);
    }

    private SweetDto mapToDto(SweetEntity sweet)
    {
        return SweetDto.builder()
                .id(sweet.getId())
                .name(sweet.getName())
                .category(sweet.getCategory())
                .price(sweet.getPrice())
                .quantity(sweet.getQuantity())
                .build();
    }
}
