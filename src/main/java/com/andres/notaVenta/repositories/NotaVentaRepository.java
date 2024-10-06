package com.andres.notaVenta.repositories;

import com.andres.notaVenta.entities.NotaVenta;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NotaVentaRepository extends JpaRepository<NotaVenta, Long> {

    Page<NotaVenta> findByVendedorNombre(String username, Pageable pageable);
}
