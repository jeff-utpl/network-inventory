package com.proyectosjava.network_inventory.infrastructure.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        final String authHeader = request.getHeader("Authorization");
        final String jwt;
        final String username;

        //1. Si no hay cabecera Authorization o empieza por Bearer, ignoramos
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        //2. Extraer el token
        jwt = authHeader.substring(7);
        username = jwtService.extraerUsername(jwt);

        //3. Si el usuario existe y no está ya autenticado en esta petición
        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            if (jwtService.esTokenValido(jwt, username)) {

                //4. Crear la credencial oficial de Spring
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(username, null, null /*aquí irían los roles/permisos*/);

                //5. Registrar el usuario en el contexto de seguridad
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }

        //6. Registrar al usuario en el contexto de seguridad
        filterChain.doFilter(request, response);

    }
}
