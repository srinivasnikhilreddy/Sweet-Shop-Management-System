package com.iss.services;

import com.iss.dtos.CreateSweetRequestDto;
import com.iss.dtos.SweetDto;
import com.iss.dtos.UpdateSweetRequestDto;
import com.iss.models.SweetEntity;
import com.iss.repositories.SweetRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class SweetService
{
    private final SweetRepository sweetRepository;

    @Value("${sweetshop.sweets.upload.dir}")
    private String uploadDir;

    @Value("${sweetshop.sweets.default.avatar:/sweet-images/default.png}")
    private String defaultImage;

    public SweetDto addSweet(CreateSweetRequestDto createSweetRequestDto, MultipartFile image)
    {
        SweetEntity sweetEntity = SweetEntity.builder()
                .name(createSweetRequestDto.getName())
                .category(createSweetRequestDto.getCategory())
                .price(createSweetRequestDto.getPrice())
                .quantity(createSweetRequestDto.getQuantity())
                .build();

        if(image != null && !image.isEmpty()){
            sweetEntity.setImageUrl(saveImage(image));
        }else{
            sweetEntity.setImageUrl(defaultImage);
        }

        return mapToDto(sweetRepository.save(sweetEntity));
    }

    private String saveImage(MultipartFile image)
    {
        try{
            String filename = UUID.randomUUID() + "_" +
                    StringUtils.cleanPath(Objects.requireNonNull(image.getOriginalFilename()));

            File dir = new File(uploadDir);
            if(!dir.exists()) dir.mkdirs();

            image.transferTo(new File(dir, filename));
            return "/sweet-images/" + filename;

        }catch(Exception ex){
            throw new RuntimeException("Image upload failed");
        }
    }

    public SweetDto updateSweet(Long id, UpdateSweetRequestDto updateSweetRequestDto)
    {
        SweetEntity sweetEntity = sweetRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Sweet not found"));

        sweetEntity.setName(updateSweetRequestDto.getName());
        sweetEntity.setCategory(updateSweetRequestDto.getCategory());
        sweetEntity.setPrice(updateSweetRequestDto.getPrice());
        sweetEntity.setQuantity(updateSweetRequestDto.getQuantity());

        return mapToDto(sweetEntity);
    }

    public void deleteSweet(Long id)
    {
        if(!sweetRepository.existsById(id)){
            throw new RuntimeException("Sweet not found");
        }
        sweetRepository.deleteById(id);
    }

    public List<SweetDto> getAllSweets()
    {
        return sweetRepository.findAll()
                .stream()
                .map(this::mapToDto)
                .toList();
    }

    public List<SweetDto> searchSweets(String name, String category, BigDecimal minPrice, BigDecimal maxPrice)
    {
        if(name != null){
            return sweetRepository.findByNameContainingIgnoreCase(name)
                    .stream().map(this::mapToDto).toList();
        }
        if(category != null){
            return sweetRepository.findByCategoryIgnoreCase(category)
                    .stream().map(this::mapToDto).toList();
        }
        if(minPrice != null && maxPrice != null){
            return sweetRepository.findByPriceBetween(minPrice, maxPrice)
                    .stream().map(this::mapToDto).toList();
        }
        return getAllSweets();
    }

    private SweetDto mapToDto(SweetEntity sweetEntity)
    {
        return SweetDto.builder()
                .id(sweetEntity.getId())
                .name(sweetEntity.getName())
                .category(sweetEntity.getCategory())
                .price(sweetEntity.getPrice())
                .quantity(sweetEntity.getQuantity())
                .imageUrl(sweetEntity.getImageUrl())
                .build();
    }
}
