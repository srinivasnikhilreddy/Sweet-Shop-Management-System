package com.iss.securities;

import com.iss.models.RefreshTokenEntity;
import com.iss.models.UserEntity;
import com.iss.repositories.RefreshTokenRepository;
import com.iss.repositories.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RefreshTokenService
{
    private final RefreshTokenRepository refreshTokenRepository;
    private final UserRepository userRepository;

    private static final long REFRESH_TOKEN_VALIDITY = 7*24*60*60*1000L; //7 days

    @Transactional
    public RefreshTokenEntity createRefreshToken(String email)
    {
        //remove any existing tokens for this userEntity
        refreshTokenRepository.deleteByEmail(email);

        UserEntity userEntity = userRepository.findByUsername(email).orElseThrow(() -> new RuntimeException("UserEntity not found"));

        RefreshTokenEntity token = RefreshTokenEntity.builder()
                .email(email)
                .userEntity(userEntity)
                .refreshToken(UUID.randomUUID().toString())
                .expiryDate(Instant.now().plusMillis(REFRESH_TOKEN_VALIDITY))
                .build();

        return refreshTokenRepository.save(token);
    }

    public Optional<RefreshTokenEntity> validateRefreshToken(String token)
    {
        return refreshTokenRepository.findByRefreshToken(token)
                .filter(rt -> rt.getExpiryDate().isAfter(Instant.now()));
    }

    @Transactional
    public Optional<RefreshTokenEntity> rotateRefreshToken(String oldToken)
    {
        return validateRefreshToken(oldToken).map(rt -> {
            refreshTokenRepository.delete(rt);
            return createRefreshToken(rt.getEmail());
        });
    }

    public void deleteByEmail(String email)
    {
        refreshTokenRepository.deleteByEmail(email);
    }
}
