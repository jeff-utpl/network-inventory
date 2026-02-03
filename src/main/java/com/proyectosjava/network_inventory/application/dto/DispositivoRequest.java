package com.proyectosjava.network_inventory.application.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

//esta clase representa los datos que va a pasar el usuario
@Data
@AllArgsConstructor
public class DispositivoRequest {

    private String nombre;
    private String ip;
    private String tipo;
}
