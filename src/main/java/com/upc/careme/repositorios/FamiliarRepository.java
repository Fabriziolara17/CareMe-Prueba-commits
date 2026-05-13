package com.upc.careme.repositorios;

import com.upc.careme.entidades.Familiar;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FamiliarRepository extends JpaRepository<Familiar, Integer> {
}