package com.andres.notaVenta.Dtos;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter @Setter @NoArgsConstructor
public class ProductoDto {
    private Long id;
    private String nombre;
    private BigDecimal precioCosto;

    public ProductoDto(Long id, String nombre, BigDecimal precioCosto) {
        this.id = id;
        this.nombre = nombre;
        this.precioCosto = precioCosto;
    }
}
