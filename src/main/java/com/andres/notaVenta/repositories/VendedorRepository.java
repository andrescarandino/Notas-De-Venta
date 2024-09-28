package com.andres.notaVenta.repositories;

import com.andres.notaVenta.entities.Vendedor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface VendedorRepository extends JpaRepository<Vendedor, Long> {

    Optional<Vendedor> findByAppUserUsernameIgnoreCase(String username);

    @Query("SELECT v FROM Vendedor v JOIN FETCH v.appUser WHERE v.id = :id")
    Optional<Vendedor> findVendedorWithAppUserById(@Param("id") Long id);

    @Query("SELECT v FROM Vendedor v JOIN FETCH v.appUser")
    List<Vendedor> findAllVendedoresWithAppUser();
}
