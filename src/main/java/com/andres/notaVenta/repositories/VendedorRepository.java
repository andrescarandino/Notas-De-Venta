package com.andres.notaVenta.repositories;

import com.andres.notaVenta.entities.Vendedor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VendedorRepository extends JpaRepository<Vendedor, Long> {
}
