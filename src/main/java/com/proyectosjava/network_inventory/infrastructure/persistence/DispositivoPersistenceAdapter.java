package com.proyectosjava.network_inventory.infrastructure.persistence;

import com.proyectosjava.network_inventory.domain.model.Dispositivo;
import com.proyectosjava.network_inventory.domain.repository.IDispositivoRepository;
import com.proyectosjava.network_inventory.infrastructure.persistence.entities.DispositivoEntity;
import com.proyectosjava.network_inventory.infrastructure.persistence.repositories.JpaDispositivoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


//esta clase hace de traductor entre el dominio y la base de datos
//su trabajo es convertir objetos de un tipo en otro
@Component
@RequiredArgsConstructor
public class DispositivoPersistenceAdapter implements IDispositivoRepository {
    private final JpaDispositivoRepository dispositivoRepository;

    @Override
    public Dispositivo guardarDispositivo(Dispositivo dispositivo) {
        //Paso A: traducir de dominio a entidad
        DispositivoEntity entity = new DispositivoEntity();
        entity.setNombre(dispositivo.getNombre());
        entity.setIp(dispositivo.getIp());
        entity.setTipo(dispositivo.getTipo());
        entity.setActivo(dispositivo.isActivo());

        //Paso B: guardar en la base de datos
        DispositivoEntity entityGuardada = dispositivoRepository.save(entity);

        //Paso C: traducir de vuelta de entidad a dominio
        return mapToDomain(entityGuardada);
    }

    @Override
    public Optional<Dispositivo> buscarPorId(Long id) {
        return dispositivoRepository.findById(id)
                .map(this::mapToDomain);
    }

    @Override
    public List<Dispositivo> listarDispositivos() {
        return dispositivoRepository.findAll()
                .stream()
                .map(this::mapToDomain)
                .collect(Collectors.toList());
    }

    //metodo privado auxiliar para no repetir código
    private Dispositivo mapToDomain(DispositivoEntity entity) {
        return Dispositivo.builder()
                .id(entity.getId())
                .nombre(entity.getNombre())
                .ip(entity.getIp())
                .tipo(entity.getTipo())
                .activo(entity.getActivo())
                .build();
    }
}
