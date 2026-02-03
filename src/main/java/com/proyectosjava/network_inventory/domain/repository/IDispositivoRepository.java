package com.proyectosjava.network_inventory.domain.repository;

import com.proyectosjava.network_inventory.domain.model.Dispositivo;

import java.util.List;
import java.util.Optional;

//Esta interface es prácticamente el puerto de salida en la arquitectura hexagonal
public interface IDispositivoRepository {

    Dispositivo guardarDispositivo(Dispositivo dispositivo);

    Optional<Dispositivo> buscarPorId(Long id);

    List<Dispositivo> listarDispositivos();
}
