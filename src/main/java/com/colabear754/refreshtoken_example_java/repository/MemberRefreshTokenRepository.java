package com.colabear754.refreshtoken_example_java.repository;

import com.colabear754.refreshtoken_example_java.entity.MemberRefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface MemberRefreshTokenRepository extends JpaRepository<MemberRefreshToken, UUID> {
    MemberRefreshToken findByRefreshToken(String refreshToken);
}
