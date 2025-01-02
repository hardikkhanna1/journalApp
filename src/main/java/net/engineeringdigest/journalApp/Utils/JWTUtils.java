package net.engineeringdigest.journalApp.Utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class JWTUtils {

    private String secretKey = "Iamtestingspringbootsecretkeyinmyapplicationsopleaseignorethis";

    public String generateToken(String username){

        Map<String,Object> claims= new HashMap<>();

        return createToken(claims,username);

    }

    public String createToken(Map<String,Object> claims, String username){

        return Jwts.builder()
                .claims(claims)
                .subject(username)
                .header().empty().add("typ","JWT")
                .and()
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis()+ 1000*60*5))
                .signWith(getSigningKey())
                .compact();
    }

    public SecretKey getSigningKey(){

        return Keys.hmacShaKeyFor(secretKey.getBytes());
    }

    public String extractUserName(String token) {

        return extractAllclaims(token).getSubject();
    }

    private Claims extractAllclaims(String token) {
        return Jwts.parser()
                .verifyWith(getSigningKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    public boolean validateToken(String jwt, String username) {
        String extractedUsername = extractUserName(jwt);
        return (extractedUsername.equals(username) && !isExpired(jwt));
    }

    private boolean isExpired(String jwt) {
        return extractAllclaims(jwt).getExpiration().before(new Date());
    }
}
