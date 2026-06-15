package com.example.matching.global.security;

import com.example.matching.entity.Role;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.junit.jupiter.api.Test;

import java.nio.charset.StandardCharsets;
import java.util.function.Consumer;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class JwtProviderTest {

    private static final String SECRET = "momentlit-matchings-service-jwt-secret-key-for-test-1234567890";

    private final JwtProvider jwtProvider = new JwtProvider(SECRET);

    @Test
    void getRoleReturnsRoleFromRoleClaim() {
        String token = createToken(builder -> builder.claim("role", "ROLE_USER"));

        assertThat(jwtProvider.getRole(token)).isEqualTo(Role.USER);
    }

    @Test
    void getRoleThrowsWhenRoleClaimIsMissingOrBlank() {
        String missingRoleToken = createToken(builder -> {
        });
        String blankRoleToken = createToken(builder -> builder.claim("role", " "));

        assertThatThrownBy(() -> jwtProvider.getRole(missingRoleToken))
                .isInstanceOf(JwtException.class)
                .hasMessage("role claim is required");
        assertThatThrownBy(() -> jwtProvider.getRole(blankRoleToken))
                .isInstanceOf(JwtException.class)
                .hasMessage("role claim is required");
    }

    private String createToken(Consumer<io.jsonwebtoken.JwtBuilder> customizer) {
        io.jsonwebtoken.JwtBuilder builder = Jwts.builder().subject("user-1");
        customizer.accept(builder);

        return builder.signWith(Keys.hmacShaKeyFor(SECRET.getBytes(StandardCharsets.UTF_8)))
                .compact();
    }
}
