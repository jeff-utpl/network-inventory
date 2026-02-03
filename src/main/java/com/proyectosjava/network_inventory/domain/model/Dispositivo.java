package com.proyectosjava.network_inventory.domain.model;

import lombok.Builder;
import lombok.Getter;


@Getter
@Builder //implementa el patrón de diseño builder de manera automática
public class Dispositivo {

    private final Long id;
    private final String nombre;
    private final String ip;
    private final String tipo;
    private final boolean activo;

    public boolean validarIp() {
        if (ip == null || ip.isEmpty()) {
            throw new IllegalArgumentException("La IP no puede estar vacía o ser nula.");
        } else {
            return true;
        }
    }
}
