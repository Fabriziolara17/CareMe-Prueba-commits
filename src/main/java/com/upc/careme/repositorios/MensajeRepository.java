package com.upc.careme.repositorios;

import com.upc.careme.entidades.Mensaje;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface MensajeRepository extends JpaRepository<Mensaje, Integer> {
    List<Mensaje> findByServicioIdServicioOrderByFechaEnvioAsc(Integer idServicio);
}