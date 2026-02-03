package com.proyectosjava.network_inventory.infrastructure.persistence.repositories;

import com.proyectosjava.network_inventory.infrastructure.persistence.entities.DispositivoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

//esta clase es una interfaz de Spring que hace la magia de guardar. Es el camión que lleva los datos a la base de datos
//sirve para que Spring sepa que tabla usar
public interface JpaDispositivoRepository extends JpaRepository<DispositivoEntity, Long> {

}
