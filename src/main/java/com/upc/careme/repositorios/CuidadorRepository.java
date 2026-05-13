package com.upc.careme.repositorios;

import com.upc.careme.entidades.Cuidador;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CuidadorRepository extends JpaRepository<Cuidador, Integer> {
    List<Cuidador> findByEspecialidadContainingIgnoreCase(String especialidad);
    List<Cuidador> findByUbicacionContainingIgnoreCase(String ubicacion);
    
    @Query("SELECT c FROM Cuidador c WHERE " +
           "(:ubicacion IS NULL OR LOWER(c.ubicacion) LIKE LOWER(CONCAT('%', :ubicacion, '%'))) AND " +
           "(:especialidad IS NULL OR LOWER(c.especialidad) LIKE LOWER(CONCAT('%', :especialidad, '%'))) AND " +
           "(:disponibilidad IS NULL OR LOWER(c.disponibilidadTexto) LIKE LOWER(CONCAT('%', :disponibilidad, '%'))) " +
           "ORDER BY c.calificacionPromedio DESC")
    List<Cuidador> buscarPorFiltros(@Param("ubicacion") String ubicacion,
                                    @Param("especialidad") String especialidad,
                                    @Param("disponibilidad") String disponibilidad);

    @Query("SELECT DISTINCT c FROM Cuidador c JOIN c.condiciones cc WHERE " +
           "LOWER(cc.nombreCondicion) LIKE LOWER(CONCAT('%', :condicion, '%')) " +
           "ORDER BY c.calificacionPromedio DESC")
    List<Cuidador> buscarPorCondicionMedica(@Param("condicion") String condicion);
}