package com.tvoseguridadelectronica.OSS.Config;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import com.tvoseguridadelectronica.OSS.Domain.Employee;

import static com.tvoseguridadelectronica.OSS.Domain.Constants.SIGNING_KEY;
import static com.tvoseguridadelectronica.OSS.Domain.Constants.ACCESS_TOKEN_VALIDITY_SECONDS;

import java.io.Serializable;
import java.util.Date;
import java.util.function.Function;

@Component
public class JwtTokenUtil implements Serializable {
	
	@Autowired
    private UserDetailsService userDetailsService;

    public String getUsernameFromToken(String token) {
        return getClaimFromToken(token, Claims::getSubject);
    }

    public Date getExpirationDateFromToken(String token) {
        return getClaimFromToken(token, Claims::getExpiration);
    }

    public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = getAllClaimsFromToken(token);
        return claimsResolver.apply(claims);
    }

    private Claims getAllClaimsFromToken(String token) {
        return Jwts.parser()
                .setSigningKey(SIGNING_KEY)
                .parseClaimsJws(token)
                .getBody();
    }

    private Boolean isTokenExpired(String token) {
        final Date expiration = getExpirationDateFromToken(token);
        return expiration.before(new Date());
    }

    public String generateToken(Employee employee) {
        return doGenerateToken(employee);
    }

    private String doGenerateToken(Employee employee) {
    	
        Claims claims = Jwts.claims().setSubject(employee.getUsername());
        //claims.put("scopes", new SimpleGrantedAuthority(employee.getRol().getRolName()));

        return Jwts.builder()
                .setClaims(claims)
                .setIssuer("com.tvoseguridadelectronica.OSS")
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + ACCESS_TOKEN_VALIDITY_SECONDS*1000))
                .signWith(SignatureAlgorithm.HS256, SIGNING_KEY).claim("role", employee.getRole().getName())
                .compact();
    }

        
    public Boolean validateToken(String token, UserDetails userDetails) {
        final String username = getUsernameFromToken(token);
        return (
              username.equals(userDetails.getUsername())
                    && !isTokenExpired(token));
    }
     
    
    
}
