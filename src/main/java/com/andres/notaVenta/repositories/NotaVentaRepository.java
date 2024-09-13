package com.andres.notaVenta.repositories;

import com.andres.notaVenta.entities.NotaVenta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NotaVentaRepository extends JpaRepository<NotaVenta, Long> {
}
