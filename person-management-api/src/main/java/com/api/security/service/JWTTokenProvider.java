package com.api.security.service;

import com.api.security.config.JwtConfigPropeties;
import com.api.security.model.UserPrincipal;
import io.jsonwebtoken.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;


@Component
@Slf4j
@RequiredArgsConstructor
public class JWTTokenProvider {

   private final JwtConfigPropeties jwtConfigPropeties;

    public String generateToken(UserPrincipal userPrincipal){

        List<String> roles = userPrincipal
                .getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());

        return Jwts
                .builder()
                .setIssuer("API APP")
                .setSubject(userPrincipal.getUsername())
                .setIssuedAt(new Date())
                .setExpiration(new Date(new Date().getTime() + jwtConfigPropeties.getExpirationInMs() * 10000))
                .claim("Roles", roles)
                .signWith(SignatureAlgorithm.HS512, jwtConfigPropeties.getSecret())
                .compact();
    }

    public boolean validateToken(String jwt) {
        try {
            Jwts.parser().setSigningKey(jwtConfigPropeties.getSecret()).parseClaimsJws(jwt);
            return true;
        } catch (SignatureException  ex) {
            log.error("Invalid JWT signature");
        } catch (MalformedJwtException ex) {
            log.error("Invalid JWT token");
        } catch (ExpiredJwtException ex) {
            log.error("Expired JWT token");
        } catch (UnsupportedJwtException ex) {
            log.error("Unsupported JWT token");
        } catch (IllegalArgumentException ex) {
            log.error("JWT claims string is empty.");
        }
        return false;


    }
    public String getUserNameFromToken(String token){
        return  Jwts.parser().setSigningKey(jwtConfigPropeties.getSecret()).parseClaimsJws(token).getBody().getSubject();
    }


}
