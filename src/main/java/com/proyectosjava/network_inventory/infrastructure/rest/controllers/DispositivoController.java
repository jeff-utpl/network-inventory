package com.proyectosjava.network_inventory.infrastructure.rest.controllers;

import com.proyectosjava.network_inventory.application.dto.DispositivoRequest;
import com.proyectosjava.network_inventory.application.service.CrearDispositivoService;
import com.proyectosjava.network_inventory.domain.model.Dispositivo;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/dispositivos")
@RequiredArgsConstructor
public class DispositivoController {

    private final CrearDispositivoService crearDispositivoService;

    @PostMapping
    public ResponseEntity<Dispositivo> crear(@RequestBody DispositivoRequest request) {
        Dispositivo dispositivoCreado = crearDispositivoService.ejecutar(request);
        return new ResponseEntity<>(dispositivoCreado, HttpStatus.CREATED);
    }
}
