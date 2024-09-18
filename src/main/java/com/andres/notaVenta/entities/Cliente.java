package com.andres.notaVenta.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "clientes")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Cliente {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String cuentaCorriente;
    private String email;
    @ManyToOne
    @JoinColumn(name = "vendedor_id")
    private Vendedor vendedor;

    @OneToMany(mappedBy = "cliente")
    private List<NotaVenta> notasVentas;

    public Cliente(Long id, String cuentaCorriente, String email) {
        this.id = id;
        this.cuentaCorriente = cuentaCorriente;
        this.email = email;
    }

    @Override
    public String toString() {
        return "Cliente{" +
                "id=" + id +
                ", cuentaCorriente='" + cuentaCorriente + '\'' +
                ", email='" + email + '\'' +
                ", notasVentas=" + notasVentas +
                '}';
    }
}
