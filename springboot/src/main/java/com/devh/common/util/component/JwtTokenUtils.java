package com.devh.common.util.component;

import com.devh.common.exception.JwtException;
import com.devh.common.exception.JwtExpiredException;
import com.devh.common.exception.JwtInvalidException;
import com.devh.common.util.ExceptionUtils;
import io.jsonwebtoken.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

@Component
@Slf4j
public class JwtTokenUtils {
    
    @Value("${jwt.auth.app}")
    private String appName;
    @Value("${jwt.auth.secret_key}")
    private String secretKey;
    @Value("${jwt.auth.expires_in}")
    private int expiresIn; // seconds

    /* default signature algorithm */
    private final SignatureAlgorithm SIGNATURE_ALGORITHM = SignatureAlgorithm.HS256;

    /**
     * Rules
     * WebUser 테이블의 id를 Token의 Subject에 저장
     */
    public String getIdFromToken(String token) throws JwtException, JwtExpiredException, JwtInvalidException {
        try {
            return getClaimsFromToken(token).getSubject();
        } catch (JwtExpiredException | JwtInvalidException je) {
            throw je;
        } catch(Exception e) {
            throw new JwtException(e.getMessage());
        }
    }

    public String getIdFromRequest(HttpServletRequest request) throws JwtException {
        try {
            final Claims claims = this.getClaimsFromToken(this.getAuthHeaderFromHeader(request));
            return claims.getSubject();
        } catch(Exception e) {
            log.warn(ExceptionUtils.stackTraceToString(e));
            throw new JwtException(e.getMessage());
        }
    }

    public String generateToken(String id) throws JwtException {
        try {
            return Jwts.builder()
                    .setIssuer(appName)
                    .setSubject(id)
                    .setIssuedAt(new Date())
                    .setExpiration(generateExpirationDate())
                    .signWith(SIGNATURE_ALGORITHM, secretKey.getBytes("UTF-8"))
                    .compact();
        } catch (Exception e) {
            log.warn(ExceptionUtils.stackTraceToString(e));
            throw new JwtException(e.getMessage());
        }
    }

    /* DB 조회 결과가 담긴 UserDetails에서 유저명 비교 */
    public boolean validateToken(String token, UserDetails userDetails) throws JwtInvalidException {
        try {
            final String id = this.getIdFromToken(token);
            return (
                id != null &&
                        id.equals(userDetails.getUsername()) &&
                        !isTokenExpired(token)
            );
        } catch (Exception e) {
            log.warn(ExceptionUtils.stackTraceToString(e));
            throw new JwtInvalidException(e.getMessage());
        }
    }

    public String getAuthHeaderFromHeader(HttpServletRequest request) {
        return request.getHeader("Authorization");
    }

    private Date generateExpirationDate() {
        final long nowMillis = System.currentTimeMillis();
        return new Date(nowMillis + (expiresIn * 1000L));
    }

    private Claims getClaimsFromToken(String token) throws JwtException, JwtExpiredException, JwtInvalidException {
        try {
            return Jwts.parser()
                        .setSigningKey(secretKey.getBytes("UTF-8"))
                        .parseClaimsJws(token)
                        .getBody();
        } catch (ExpiredJwtException eje) {
            log.warn(ExceptionUtils.stackTraceToString(eje));
            throw new JwtExpiredException(eje.getMessage());
        } catch (MalformedJwtException mje) {
            log.warn(ExceptionUtils.stackTraceToString(mje));
            throw new JwtInvalidException(mje.getMessage());
        } catch(Exception e) {
            log.warn(ExceptionUtils.stackTraceToString(e));
            throw new JwtException(e.getMessage());
        }
    }

    private boolean isTokenExpired(String token) throws JwtException, JwtInvalidException, JwtExpiredException {
        Date expireDate = getExpirationDate(token);
        return expireDate.before(new Date());
    }

    private Date getExpirationDate(String token) throws JwtInvalidException, JwtExpiredException, JwtException {
        final Claims claims = this.getClaimsFromToken(token);
        return claims.getExpiration();
    }
}
