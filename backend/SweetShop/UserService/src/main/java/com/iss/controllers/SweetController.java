package com.iss.controllers;

import com.iss.dtos.*;
import com.iss.services.InventoryService;
import com.iss.services.SweetService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/sweets")
@RequiredArgsConstructor
public class SweetController
{
    private final SweetService sweetService;
    private final InventoryService inventoryService;

    /* ADMIN only
    POST http://localhost:9070/api/sweets/add
    Headers:
            Authorization: Bearer <ADMIN_TOKEN>
            Content-Type: multipart/form-data
    Body (form-data):
        Key	    Type	Value
        sweet	Text	{ "name":"Ladoo", "category":"Indian", "price":150, "quantity":40 }
        image	File	(sweet image)*/
    @PostMapping(value = "/add", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public SweetDto add(
            @RequestPart("sweet") CreateSweetRequestDto createSweetRequestDto,
            @RequestPart(value = "image", required = false) MultipartFile image)
    {
        return sweetService.addSweet(createSweetRequestDto, image);
    }

    /* USER / ADMIN
    GET http://localhost:9070/api/sweets/getAll
    Authorization: Bearer <USER_OR_ADMIN_TOKEN>*/
    @GetMapping("/getAll")
    public List<SweetDto> getAll()
    {
        return sweetService.getAllSweets();
    }

    /* USER / ADMIN
    GET http://localhost:9070/api/sweets/search?name=ladoo
    GET http://localhost:9070/api/sweets/search?category=Indian
    GET http://localhost:9070/api/sweets/search?minPrice=100&maxPrice=200
    Authorization: Bearer <USER_OR_ADMIN_TOKEN>*/
    @GetMapping("/search")
    public List<SweetDto> search(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String category,
            @RequestParam(required = false) BigDecimal minPrice,
            @RequestParam(required = false) BigDecimal maxPrice)
    {
        return sweetService.searchSweets(name, category, minPrice, maxPrice);
    }

    /* ADMIN only
    PUT http://localhost:9070/api/sweets/update/1
    Authorization: Bearer <ADMIN_TOKEN>
    Content-Type: application/json
    {
      "name": "Dry Fruit Ladoo",
      "category": "Indian",
      "price": 200,
      "quantity": 30
    }*/
    @PutMapping("/update/{id}")
    public SweetDto update(@PathVariable Long id,
                           @RequestBody UpdateSweetRequestDto updateSweetRequestDto)
    {
        return sweetService.updateSweet(id, updateSweetRequestDto);
    }

    /* ADMIN only
    DELETE http://localhost:9070/api/sweets/delete/1
    Authorization: Bearer <ADMIN_TOKEN>*/
    @DeleteMapping("/delete/{id}")
    public void delete(@PathVariable Long id)
    {
        sweetService.deleteSweet(id);
    }

    /* USER / ADMIN
    POST http://localhost:9070/api/sweets/1/purchase
    Headers:
            Authorization: Bearer <USER_OR_ADMIN_TOKEN>
            Content-Type: application/json
    Body:
        {
          "quantity": 2
        }*/
    @PostMapping("/{id}/purchase")
    public SweetDto purchase(@PathVariable Long id,
                             @RequestBody PurchaseRequestDto purchaseRequestDto)
    {
        return inventoryService.purchaseSweet(id, purchaseRequestDto.getQuantity());
    }

    /* ADMIN only
    POST http://localhost:9070/api/sweets/1/restock
    Headers:
            Authorization: Bearer <ADMIN_TOKEN>
            Content-Type: application/json
    Body:
        {
          "quantity": 20
        }*/
    @PostMapping("/{id}/restock")
    public SweetDto restock(@PathVariable Long id,
                            @RequestBody RestockRequestDto restockRequestDto)
    {
        return inventoryService.restockSweet(id, restockRequestDto.getQuantity());
    }
}
