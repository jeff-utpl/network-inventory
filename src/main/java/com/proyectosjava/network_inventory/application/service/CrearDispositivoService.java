package com.proyectosjava.network_inventory.application.service;

import com.proyectosjava.network_inventory.application.dto.DispositivoRequest;
import com.proyectosjava.network_inventory.domain.model.Dispositivo;
import com.proyectosjava.network_inventory.domain.repository.IDispositivoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor //crea el constructor para los campos final
public class CrearDispositivoService {

    private final IDispositivoRepository dispositivoRepository; //inyección de dependencia, no necesita que
    //hagamos el constructor manual porque ya estamos usando @RequiredArgsConstructor

    public Dispositivo ejecutar(DispositivoRequest request) {

        Dispositivo dispositivo = Dispositivo.builder()
                .nombre(request.getNombre())
                .ip(request.getIp())
                .tipo(request.getTipo())
                .activo(true)
                .build();

        dispositivo.validarIp();
        return dispositivoRepository.guardarDispositivo(dispositivo);
    }
}
