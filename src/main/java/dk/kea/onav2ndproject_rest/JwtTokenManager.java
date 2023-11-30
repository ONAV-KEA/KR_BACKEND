package dk.kea.onav2ndproject_rest;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtTokenManager {
    public static final long TOKEN_VALIDITY = 10 * 60 * 60 * 1000; // 10 timer
    // aha: Below is the server's private key. Which is used to generate new tokens. Length: Minimum 512 bits.
    // Which corresponds to minimum 86 characters in cleartext.
    @Value("${secret}")
    private String jwtSecret;
    public String generateJwtToken(UserDetails userDetails) {
        System.out.println("TokenManager generateJwtToken(UserDetails) call: 7");
        Map<String, Object> claims = new HashMap<>();
        return Jwts.builder().setClaims(claims).setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + TOKEN_VALIDITY ))
                .signWith(getSignInKey(),SignatureAlgorithm.HS512 )
                .compact();
                //.signWith(SignatureAlgorithm.HS512, jwtSecret).compact(); // before Spring 3
    }

    private Key getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(jwtSecret);
        return Keys.hmacShaKeyFor(keyBytes);
    }
    public Boolean validateJwtToken(String token, UserDetails userDetails) {
        System.out.println("TokenManager validateJwtToken(String token, UserDetails) With token: Call: B");
        String username = getUsernameFromToken(token);
        Claims claims = getClaims(token);
        Boolean isTokenExpired = claims.getExpiration().before(new Date());
        return (username.equals(userDetails.getUsername()) && !isTokenExpired);
    }
    public String getUsernameFromToken(String token) {
        System.out.println("TokenManager getUsernameFromToken(String token) With token: Call: A");
        //Claims claims = Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody(); // before Spring 3
        Claims claims = getClaims(token);
        if(claims != null){
            return claims.getSubject();
        }else {
            return "no user found";
        }
    }

    private Claims getClaims(String token){
        try{
            Claims claims = Jwts
                    .parserBuilder()
                    .setSigningKey(getSignInKey())
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
            return claims;
        }catch (Exception e){
            System.out.println("could not parse JWT token for claims");
        }
        return null;
    }
}
