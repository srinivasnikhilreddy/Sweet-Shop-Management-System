package com.iss.repositories;

import com.iss.models.SweetEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.math.BigDecimal;
import java.util.List;

@Repository
public interface SweetRepository extends JpaRepository<SweetEntity, Long>
{
    List<SweetEntity> findByNameContainingIgnoreCase(String name);
    List<SweetEntity> findByCategoryIgnoreCase(String category);
    List<SweetEntity> findByPriceBetween(
            BigDecimal min,
            BigDecimal max
    );
}

