package com.proyectosjava.network_inventory.infrastructure.rest.controllers;

import com.proyectosjava.network_inventory.infrastructure.rest.dto.LoginRequest;
import com.proyectosjava.network_inventory.infrastructure.security.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthRestController {

    private final JwtService jwtService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        //Por ahora, simulamos una validación sencilla para avanzar
        if ("admin".equals(request.getUsername()) && "1234".equals(request.getPassword())) {
            String token = jwtService.generarToken(request.getUsername());
            return ResponseEntity.ok(Map.of("token", token));
        }

        return ResponseEntity.status(401).body("Credenciales inválidas");
    }
}
