package dg.peffy_backend.security;

import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.stereotype.Service;

import dg.peffy_backend.config.JwtProperties;
import dg.peffy_backend.user.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Service 
public class JwtService {

    private final long expirationMs;
    private final SecretKey key;

    public JwtService(JwtProperties props) {
        this.key = Keys.hmacShaKeyFor(Decoders.BASE64.decode(props.secret()));
        this.expirationMs = props.expirationMs();
    }
    
    public String generateAccessToken(User user){
        Date now = new Date();
        return Jwts.builder()
            .subject(user.getId().toString())
            .issuedAt(now)
            .expiration(new Date(now.getTime() + expirationMs))
            .signWith(key)
            .compact();
    }

    private Claims parseClaims(String token){
        return Jwts.parser()
            .verifyWith(key)
            .build()
            .parseSignedClaims(token)
            .getPayload();
    }

    public boolean isValid(String token, User user){
        try{
            Claims claims = parseClaims(token);
            return  claims.getSubject().equals(user.getId().toString()) && claims.getExpiration().after(new Date());
        } catch(JwtException | IllegalArgumentException e){
            return false;
        }
    }    

    public long getAccessTokenExpirationSeconds() {
        return expirationMs / 1000;
    }
}
