package vn.edu.fpt.laboratory.service.impl;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;
import vn.edu.fpt.laboratory.service._TokenService;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author : Hoang Lam
 * @product : Charity Management System
 * @project : Charity System
 * @created : 06/09/2022 - 18:09
 * @contact : 0834481768 - hoang.harley.work@gmail.com
 **/
@Service
@Slf4j
@RequiredArgsConstructor
public class TokenServiceImpl implements _TokenService {

    private static String SECRET_KEY;
    @Value("${app.security.secret-key}")
    private void setSecretKey(String secretKey){
        SECRET_KEY = secretKey;
    }

    @Override
    public Optional<Authentication> getAuthenticationFromToken(String token) {
        Jws<Claims> jwsClaims = validateToken(token);
        if (jwsClaims == null) {
            return Optional.empty();
        }

        if(!validateTokenHeader(jwsClaims.getHeader())){
            return Optional.empty();
        }

        Claims claims = jwsClaims.getBody();

        if(!validateExpiredTime(claims)){
            return Optional.empty();
        }


        String id = claims.getId();

        String authorities = claims.get("authorities", String.class);

        Collection<? extends GrantedAuthority> grantedAuthorities = convertToAuthority(authorities);

        User principal = new User(id, "", grantedAuthorities);
        return Optional.of(new UsernamePasswordAuthenticationToken(principal, token, grantedAuthorities));

    }

    private boolean validateTokenHeader(JwsHeader jwsHeader){
        String alg = jwsHeader.getAlgorithm();
        String headerType = jwsHeader.getType();
        return alg.equals("HS256") && headerType.equals(Header.JWT_TYPE);
    }

    private boolean validateExpiredTime(Claims claims){
        Calendar calendar = Calendar.getInstance(TimeZone.getDefault());
        Long currentTime = calendar.getTimeInMillis();

        Long expirationTime = claims.get(Claims.EXPIRATION, Long.class);
        return expirationTime > currentTime;
    }

    private boolean validateId(Claims claims){
        String jti = claims.get(Claims.ID, String.class);
        try {
            UUID uuid = UUID.fromString(jti);
            return true;
        }catch (Exception ex){
            return false;
        }
    }

    private Collection<? extends GrantedAuthority> convertToAuthority(String authority){
        String[] authorities = authority.split(",");
        return Arrays.stream(authorities)
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
    }

    private Jws<Claims> validateToken(String token) {
        try {
            return getJwtParser().parseClaimsJws(token);
        }catch (Exception ex){
            return null;
        }
    }

    private static Key getSigningKey() {
        byte[] keyBytes = SECRET_KEY.getBytes(StandardCharsets.UTF_8);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    private static JwtParser getJwtParser(){
        return Jwts
                .parserBuilder()
                .setSigningKey(getSigningKey())
                .build();
    }
}
