package com.andres.notaVenta.entities;

import com.andres.notaVenta.security.AppUser;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "vendedores")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Vendedor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    private String nombre;

    @OneToOne
    @JoinColumn(name = "user_id")
    private AppUser appUser;

    @OneToMany(mappedBy = "vendedor")
    private List<NotaVenta> notasVentas;

    @OneToMany(mappedBy = "vendedor")
    private List<Cliente> clientes;

    public Vendedor(Long id, String nombre) {
        this.id = id;
        this.nombre = nombre;
    }

    @Override
    public String toString() {
        return "Vendedor{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", Username=" + getAppUser().getUsername() +
                ", roles= " + getAppUser().getRoles().toString()+
                '}';
    }
}
