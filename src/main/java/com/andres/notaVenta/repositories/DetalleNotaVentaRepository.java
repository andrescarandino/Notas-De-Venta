package com.andres.notaVenta.repositories;

import com.andres.notaVenta.entities.DetalleNotaVenta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DetalleNotaVentaRepository extends JpaRepository<DetalleNotaVenta, Long> {
}
