package com.andres.notaVenta.repositories;

import com.andres.notaVenta.entities.Vendedor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface VendedorRepository extends JpaRepository<Vendedor, Long> {

    Optional<Vendedor> findByAppUserUsername(String username);

}
