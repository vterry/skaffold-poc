package com.sample.admsys.repository;

import com.sample.admsys.model.JwtRefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JwtRefreshTokenRepository extends JpaRepository<JwtRefreshToken, String> {

}
