package com.laila.pet_symptom_tracker.securityconfig;

import com.laila.pet_symptom_tracker.entities.user.User;
import com.laila.pet_symptom_tracker.securityconfig.dto.TokenData;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import java.util.*;
import javax.crypto.SecretKey;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class JwtService {
  private static final String ROLES_CLAIM_NAME = "roles";
  private static final int JWT_EXPIRATION_MS = 3600000;
  private final SecretKey jwtSecretKey;

  public String generateTokenForUser(User user) {
    return buildToken(user);
  }

  public Optional<TokenData> readToken(String token) {
    try {
      Claims claims =
          Jwts.parser().verifyWith(jwtSecretKey).build().parseSignedClaims(token).getPayload();

      return Optional.of(
          new TokenData(
              claims.getSubject(),
              getRolesFromClaims(claims),
              claims.getIssuedAt(),
              claims.getExpiration()));
    } catch (RuntimeException ex) {
      System.out.println(
          "Exception reading JWT-token: TYPE: '"
              + ex.getClass().getName()
              + "', MESSAGE: '"
              + ex.getMessage()
              + "'");

      return Optional.empty();
    }
  }

  private String buildToken(User user) {
    long currentTimeMillis = System.currentTimeMillis();

    return Jwts.builder()
        .claims(Map.of(ROLES_CLAIM_NAME, user.getAuthorities()))
        .subject(user.getUsername())
        .issuedAt(new Date(currentTimeMillis))
        .expiration(new Date(currentTimeMillis + JWT_EXPIRATION_MS))
        .signWith(jwtSecretKey)
        .compact();
  }

  private String[] getRolesFromClaims(Claims claims) {
    Object rolesObject = claims.get(ROLES_CLAIM_NAME);

    if (rolesObject == null) {
      throw new IllegalArgumentException("'" + ROLES_CLAIM_NAME + "' claim not found");
    }

    if (!(rolesObject instanceof Iterable<?> rawRoles)) {
      throw new IllegalArgumentException("claims '" + ROLES_CLAIM_NAME + "' value is invalid");
    }

    List<String> parsedRoles = new LinkedList<>();

    for (Object o : rawRoles) {
      if (o instanceof String parsedRole) {
        parsedRoles.add(parsedRole);
      }
    }

    return parsedRoles.toArray(new String[0]);
  }
}
