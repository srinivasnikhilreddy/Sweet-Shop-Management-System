package com.iss.services;

import com.iss.dtos.CreateSweetRequestDto;
import com.iss.dtos.UpdateSweetRequestDto;
import com.iss.models.SweetEntity;
import com.iss.repositories.SweetRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SweetServiceTest
{
    @Mock
    private SweetRepository sweetRepository;

    @InjectMocks
    private SweetService sweetService;

    @Test
    void shouldAddSweetSuccessfully()
    {
        //RED: define input
        CreateSweetRequestDto createSweetRequestDto = CreateSweetRequestDto.builder()
                .name("Ladoo")
                .category("Indian")
                .price(BigDecimal.valueOf(100))
                .quantity(10)
                .build();

        SweetEntity savedEntity = SweetEntity.builder()
                .id(1L)
                .name("Ladoo")
                .category("Indian")
                .price(BigDecimal.valueOf(100))
                .quantity(10)
                .build();

        when(sweetRepository.save(any())).thenReturn(savedEntity);

        //GREEN: execute
        var result = sweetService.addSweet(createSweetRequestDto, null);

        //ASSERT
        assertNotNull(result);
        assertEquals("Ladoo", result.getName());
        assertEquals(10, result.getQuantity());

        verify(sweetRepository, times(1)).save(any());
    }

    @Test
    void shouldReturnAllSweets()
    {
        when(sweetRepository.findAll())
                .thenReturn(List.of(
                        SweetEntity.builder().name("Ladoo").build(),
                        SweetEntity.builder().name("Barfi").build()
                ));

        var result = sweetService.getAllSweets();

        assertEquals(2, result.size());
        verify(sweetRepository).findAll();
    }

    @Test
    void shouldUpdateSweetSuccessfully()
    {
        SweetEntity existing = SweetEntity.builder()
                .id(1L)
                .name("Ladoo")
                .price(BigDecimal.valueOf(100))
                .quantity(10)
                .build();

        when(sweetRepository.findById(1L))
                .thenReturn(Optional.of(existing));

        UpdateSweetRequestDto dto = UpdateSweetRequestDto.builder()
                .price(BigDecimal.valueOf(120))
                .quantity(20)
                .build();

        var result = sweetService.updateSweet(1L, dto);

        assertEquals(BigDecimal.valueOf(120), result.getPrice());
        assertEquals(20, result.getQuantity());

        verify(sweetRepository).findById(1L);
        verify(sweetRepository, never()).save(any());
    }

    @Test
    void shouldDeleteSweetSuccessfully()
    {
        when(sweetRepository.existsById(1L))
                .thenReturn(true);

        assertDoesNotThrow(() -> sweetService.deleteSweet(1L));

        verify(sweetRepository).deleteById(1L);
    }

    @Test
    void shouldSearchByName()
    {
        when(sweetRepository.findByNameContainingIgnoreCase("Ladoo"))
                .thenReturn(List.of(
                        SweetEntity.builder().name("Ladoo").build()
                ));

        var result = sweetService.searchSweets(
                "Ladoo", null, null, null
        );

        assertEquals(1, result.size());
        assertEquals("Ladoo", result.get(0).getName());

        verify(sweetRepository).findByNameContainingIgnoreCase("Ladoo");
    }
}
