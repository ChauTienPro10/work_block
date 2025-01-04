package com.work_block.main.configuration;


import com.work_block.main.repository.BlackListJwtRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.SignatureException;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class JwtTokenUtil {
    private final JwtConfig jwtConfig;

    @Autowired
    BlackListJwtRepository blackListJwtRepository;

    public JwtTokenUtil(JwtConfig jwtConfig) {
        this.jwtConfig = jwtConfig;
    }

    /**
     * tạo jwt token
     * @param username
     * @return
     */
    public String generateToken(String username, List<String> roles) {
        return Jwts.builder()
                .setSubject(username)
                .claim("role", roles)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 60 * 1000))
                .signWith(SignatureAlgorithm.HS512, jwtConfig.getSecret().getBytes())
                .compact();
    }

    /**
     * Kiểm tra token
     * @param token
     * @return
     */
    public boolean validateToken(String token) {
        if(blackListJwtRepository.findByValue(token) != null){
            return false;
        }
        try {
            Jwts.parserBuilder()
                    .setSigningKey(jwtConfig.getSecret().getBytes())
                    .build()
                    .parseClaimsJws(token);
            return true;
        } catch (ExpiredJwtException e) {
            System.out.println("JWT token has expired: " + e.getMessage());
        } catch (UnsupportedJwtException e) {
            System.out.println("JWT token is unsupported: " + e.getMessage());
        } catch (MalformedJwtException e) {
            System.out.println("JWT token is malformed: " + e.getMessage());
        } catch (SignatureException e) {
            System.out.println("JWT signature does not match: " + e.getMessage());
        } catch (IllegalArgumentException e) {
            System.out.println("JWT claims string is empty: " + e.getMessage());
        }
        return false;
    }

    /**
     * Lấy username từ token
     * @param token
     * @return
     */
    public String getUsernameFromToken(String token) {
        try {
            Claims claims = Jwts.parserBuilder()
                    .setSigningKey(jwtConfig.getSecret().getBytes())
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
            return claims.getSubject();
        } catch (Exception e) {
            System.out.println("Error extracting username from token: " + e.getMessage());
        }
        return null;
    }

    /**
     * Lấy hạn sử dụng của token
     * @param token
     * @return
     */
    public Date getExpirationFromToken(String token){
        try {
            Claims claims = Jwts.parserBuilder()
                    .setSigningKey(jwtConfig.getSecret().getBytes())
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
            return claims.getExpiration();
        } catch (Exception e) {
            System.out.println("Error extracting expiration from token: " + e.getMessage());
        }
        return null;
    }

    /**
     * Giải mã token đã hết hạn
     * @param token
     * @return
     */
    public Claims getClaimsFromExpiredToken(String token) {
        try {
            return Jwts.parserBuilder()
                    .setSigningKey(jwtConfig.getSecret().getBytes())
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
        } catch (ExpiredJwtException e) {
            return e.getClaims();
        }
    }


}
