package com.upc.careme.repositorios;

import com.upc.careme.entidades.Pago;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PagoRepository extends JpaRepository<Pago, Integer> {
}