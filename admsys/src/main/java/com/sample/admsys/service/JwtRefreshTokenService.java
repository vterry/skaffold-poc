package com.sample.admsys.service;

import com.sample.admsys.model.JwtRefreshToken;
import com.sample.admsys.model.User;
import com.sample.admsys.repository.JwtRefreshTokenRepository;
import com.sample.admsys.repository.UserRepository;
import com.sample.admsys.security.UserPrincipal;
import com.sample.admsys.security.jwt.JwtProvider;
import com.sample.admsys.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Set;
import java.util.UUID;

@Service
public class JwtRefreshTokenService {

    @Value("${app.jwt.refresh-expiration-in-ms}")
    private Long REFRESH_EXPIRATION_IN_MS;

    @Autowired
    private JwtRefreshTokenRepository jwtRefreshTokenRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtProvider jwtProvider;

    public JwtRefreshToken createRefreshToken(Long userId){
        JwtRefreshToken jwtRefreshToken = new JwtRefreshToken();
        jwtRefreshToken.setTokenId(UUID.randomUUID().toString());
        jwtRefreshToken.setUserId(userId);
        jwtRefreshToken.setCreateDate(LocalDateTime.now());
        jwtRefreshToken.setExpirationDate(LocalDateTime.now().plus(REFRESH_EXPIRATION_IN_MS, ChronoUnit.MILLIS));
        return jwtRefreshTokenRepository.save(jwtRefreshToken);
    }

    public User generateAccessTokenFromRefreshToken(String refreshTokenId){
        JwtRefreshToken jwtRefreshToken = jwtRefreshTokenRepository.findById(refreshTokenId).orElseThrow();

        if (jwtRefreshToken.getExpirationDate().isBefore(LocalDateTime.now())){
            throw new RuntimeException("JWT refresh token is not valid.");
        }

        User user = userRepository.findById(jwtRefreshToken.getUserId()).orElseThrow();

        UserPrincipal userPrincipal = new UserPrincipal(
                user.getId(),
                user.getUsername(),
                user.getPassword(),
                user,
                Set.of(SecurityUtils.convertToAuthority(user.getRole().name())));

        String accessToken = jwtProvider.generateToken(userPrincipal);

        user.setAccessToken(accessToken);
        user.setRefreshToken(refreshTokenId);

        return user;
    }

}
