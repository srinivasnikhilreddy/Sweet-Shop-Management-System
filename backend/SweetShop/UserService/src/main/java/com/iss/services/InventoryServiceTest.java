package com.iss.services;

import com.iss.dtos.SweetDto;
import com.iss.models.SweetEntity;
import com.iss.repositories.SweetRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class InventoryServiceTest
{
    @Mock
    private SweetRepository sweetRepository;

    @InjectMocks
    private InventoryService inventoryService;

    @Test
    void shouldDecreaseQuantityOnPurchase()
    {
        SweetEntity sweet = SweetEntity.builder()
                .id(1L)
                .quantity(10)
                .build();

        when(sweetRepository.findById(1L))
                .thenReturn(Optional.of(sweet));

        when(sweetRepository.save(any()))
                .thenAnswer(inv -> inv.getArgument(0));

        SweetDto dto = inventoryService.purchaseSweet(1L, 3);
        assertEquals(7, dto.getQuantity());
        verify(sweetRepository).save(sweet);
    }

    @Test
    void shouldFailIfInsufficientStock()
    {
        SweetEntity sweet = SweetEntity.builder()
                .quantity(1)
                .build();

        when(sweetRepository.findById(1L)).thenReturn(Optional.of(sweet));

        RuntimeException ex = assertThrows(
                RuntimeException.class,
                () -> inventoryService.purchaseSweet(1L, 5)
        );
        assertEquals("Insufficient stock", ex.getMessage());
    }
}
