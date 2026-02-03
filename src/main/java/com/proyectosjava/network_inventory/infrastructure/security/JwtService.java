package com.proyectosjava.network_inventory.infrastructure.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;

@Service
public class JwtService {

    //1. Una llave secreta de al menos 256 bits
    private static final Key SECRET_KEY = Keys.secretKeyFor(SignatureAlgorithm.HS256);

    //2. Metodo para generar el token
    public String generarToken(String username) {
        return Jwts.builder()
                .setSubject(username) // A quién pertenece
                .setIssuedAt(new Date(System.currentTimeMillis())) // Cuándo se creó
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 24)) //Expira en 24h
                .signWith(SECRET_KEY) // Firmar con nuestra llave secreta
                .compact(); //convertir a String
    }

    //3. Metodo para validar si el token que nos traen es real
    public boolean esTokenValido(String token, String username) {
        final String usernameDelToken = extraerUsername(token);
        return (usernameDelToken.equals(username) && !isTokenExpirado(token));
    }

    public String extraerUsername(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(SECRET_KEY)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    private boolean isTokenExpirado(String token) {
        Date expiration = Jwts.parserBuilder()
                .setSigningKey(SECRET_KEY)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getExpiration();
        return expiration.before(new Date());
    }
}
