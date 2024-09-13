package com.andres.notaVenta.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Entity
@Table(name = "productos")
@NoArgsConstructor
@Getter
@Setter
public class Producto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nombre;
    private BigDecimal precioCosto;

    @OneToMany(mappedBy = "producto", cascade = CascadeType.ALL)
    private List<DetalleNotaVenta> detallesVentas;

    public Producto(Long id, String nombre, BigDecimal precioCosto) {
        this.id = id;
        this.nombre = nombre;
        this.precioCosto = precioCosto;
    }

    @Override
    public String toString() {
        return "Producto{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", precioCosto=" + precioCosto +
                '}';
    }
}
