package com.proyectosjava.network_inventory.application.service;

import com.proyectosjava.network_inventory.application.dto.DispositivoRequest;
import com.proyectosjava.network_inventory.domain.model.Dispositivo;
import com.proyectosjava.network_inventory.domain.repository.IDispositivoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class CrearDispositivoServiceTest {

    @Mock
    private IDispositivoRepository dispositivoRepository; //falso repositorio

    @InjectMocks
    private CrearDispositivoService crearDispositivoService; //la clase que vamos a probar

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this); //inicializa los mocks
    }

    @Test
    void cuandoSeCreaDispositivoValido_entoncesRetornaDispositivo() {
        //GIVEN (preparación)
        DispositivoRequest request = new DispositivoRequest("Router HP", "192.168.30.25", "Router");
        Dispositivo dispositivoEsperado = Dispositivo.builder()
                .id(1L)
                .nombre("Router HP")
                .ip("192.168.30.25")
                .tipo("Router")
                .activo(true)
                .build();

        //configuramos el mock para que cuando llamen a guardar, devuelva nuestro dispositivo esperado
        when(dispositivoRepository.guardarDispositivo(any(Dispositivo.class))).thenReturn(dispositivoEsperado);

        //WHEN (acción)
        Dispositivo resultado = crearDispositivoService.ejecutar(request);

        //THEN (verificación)
        assertNotNull(resultado); //comprobamos que el servicio no nos devuelva un null
        assertEquals("192.168.30.25", resultado.getIp()); //comparamos si la ip del resultado es la que queríamos
        verify(dispositivoRepository, times(1)).guardarDispositivo(any(Dispositivo.class));
    }

    @Test
    void cuandoIpEsInvalida_entoncesLanzaExcepcion() {
        //GIVEN
        DispositivoRequest request = new DispositivoRequest("Router-Error", "","Router");

        //WHEN AND THEN (verificamos que lance la excepción que programamos en el dominio)
        assertThrows(RuntimeException.class, () -> {
            crearDispositivoService.ejecutar(request);
        });

    }

}
