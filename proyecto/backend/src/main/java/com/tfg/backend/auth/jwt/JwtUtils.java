package com.tfg.backend.auth.jwt;

import com.tfg.backend.auth.services.UserDetailsImpl;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class JwtUtils {
  private static final Logger logger = LoggerFactory.getLogger(JwtUtils.class);

  @Value("${jwt.jwtSecret}")
  private String jwtSecret;

  @Value("${jwt.jwtExpirationMs}")
  private int jwtExpirationMs;

  public String generateJwtToken(Authentication authentication) {
    UserDetailsImpl userPrincipal = (UserDetailsImpl) authentication.getPrincipal();

    // Creamos los claims personalizados
    Map<String, Object> claims = new HashMap<>();
    claims.put("roles", userPrincipal.getAuthorities().stream()
            .map(grantedAuthority -> grantedAuthority.getAuthority())
            .collect(Collectors.toList()));

    // Creamos el token incluyendo los claims
    return Jwts.builder()
            .setClaims(claims)
            .setSubject(userPrincipal.getUsername())
            .setIssuedAt(new Date())
            .setExpiration(new Date((new Date()).getTime() + jwtExpirationMs))
            .signWith(key(), SignatureAlgorithm.HS256)
            .compact();
  }


  private Key key() {
    //return Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtSecret));  // Si jwSecret está en Base64
    return Keys.hmacShaKeyFor(jwtSecret.getBytes()); // Si jwSecret no está en Base64
  }

  public String getUserNameFromJwtToken(String token) {
    return Jwts.parserBuilder().setSigningKey(key()).build()
               .parseClaimsJws(token).getBody().getSubject();
  }

  public boolean validateJwtToken(String authToken) {
    try {
      Jwts.parserBuilder().setSigningKey(key()).build().parse(authToken);
      return true;
    } catch (MalformedJwtException e) {
      logger.error("Token JWT no válido: {}", e.getMessage());
    } catch (ExpiredJwtException e) {
      logger.error("El token JWT ha expirado: {}", e.getMessage());
    } catch (UnsupportedJwtException e) {
      logger.error("El token JWT no es compatible: {}", e.getMessage());
    } catch (IllegalArgumentException e) {
      logger.error("JWT afirma que la cadena está vacía: {}", e.getMessage());
    }

    return false;
  }
}
