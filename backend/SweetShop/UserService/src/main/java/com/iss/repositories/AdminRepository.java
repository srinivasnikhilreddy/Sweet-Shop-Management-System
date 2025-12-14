package com.iss.repositories;

import com.iss.models.AdminEntity;
import com.iss.models.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AdminRepository extends JpaRepository<AdminEntity, Long>
{
    Optional<AdminEntity> findByUserEntity(UserEntity user);
}
