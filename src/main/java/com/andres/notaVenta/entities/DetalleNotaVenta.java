package com.andres.notaVenta.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.http.converter.json.GsonBuilderUtils;

import java.math.BigDecimal;

@Entity
@Table(name = "detallesNotasVentas")
@Getter @Setter
@NoArgsConstructor
public class DetalleNotaVenta {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "nota_venta_id")
    private NotaVenta notaVenta;

    @ManyToOne
    @JoinColumn(name = "producto_id")
    private Producto producto;

    private BigDecimal IVA;
    private String nombre;
    private BigDecimal precioCosto;
    private BigDecimal precioVenta;
    private BigDecimal subtotalCosto;
    private BigDecimal subtotalVenta;
    private BigDecimal cantidad;
    private BigDecimal ganancia; //%ganancia

    public DetalleNotaVenta(Producto producto, BigDecimal cantidad, BigDecimal ganancia, BigDecimal IVA) {
        this.cantidad = cantidad;
        this.producto = producto;
        this.nombre= producto.getNombre();
        this.IVA= IVA;
    }

    public BigDecimal calcularSubtotalCosto() {
        return this.getPrecioCosto().multiply(this.getCantidad());
    }

    public BigDecimal calcularPrecioVenta() {
        if(this.ganancia == null ){
            this.ganancia = BigDecimal.ZERO;
            return calcularSubtotalCosto();
        }
        return this.getPrecioCosto().multiply(
                BigDecimal.ONE.add(ganancia.divide(BigDecimal.valueOf(100))));

    }

    public BigDecimal calcularSubtotalVenta() {
        return calcularPrecioVenta().multiply(this.getCantidad());
    }

    public BigDecimal calcularIVADetalle(){
         return calcularSubtotalVenta().multiply(IVA).divide(BigDecimal.valueOf(100));
    }

    @Override
    public String toString() {
        return "DetalleNotaVenta{" +
                "id=" + id +
                ", notaVenta=" + notaVenta +
                ", precioCosto=" + precioCosto +
                ", precioVenta=" + precioVenta +
                ", subtotalCosto=" + subtotalCosto +
                ", subtotalVenta=" + subtotalVenta +
                ", cantidad=" + cantidad +
                ", ganancia=" + ganancia +
                "productoid=" + producto.getId() +
                '}';
    }
}
