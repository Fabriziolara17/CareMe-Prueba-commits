package com.upc.careme.repositorios;

import com.upc.careme.entidades.Calificacion;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CalificacionRepository extends JpaRepository<Calificacion, Integer> {
}