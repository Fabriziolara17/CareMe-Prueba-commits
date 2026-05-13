package com.upc.careme.repositorios;

import com.upc.careme.entidades.FamiliarPaciente;
import com.upc.careme.entidades.FamiliarPacienteId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FamiliarPacienteRepository extends JpaRepository<FamiliarPaciente, FamiliarPacienteId> {
}