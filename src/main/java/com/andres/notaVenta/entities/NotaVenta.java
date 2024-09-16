package com.andres.notaVenta.entities;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "notas_ventas")
@NoArgsConstructor
@Getter
@Setter
public class NotaVenta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private BigDecimal IVA;
    private LocalDate fechaCreacion;
    private LocalDate vencimiento;
    private Comprobante comprobante;
    private BigDecimal tipoCambio;
    private BigDecimal interesMensual;
    private String formaDePago;

    //Hay que calcular
    private BigDecimal totalUSD;
    private BigDecimal subtotalUSD;
    private BigDecimal totalARS;
    private BigDecimal totalIVA;
    private BigDecimal totalIntereses;

    //por defecto en espera
    private Estado estado;

    @OneToMany(mappedBy = "notaVenta", cascade = CascadeType.ALL)
    private List<DetalleNotaVenta> detalles = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "cliente_id")
    private Cliente cliente;

    @ManyToOne
    @JoinColumn(name = "vendedor_id")
    private Vendedor vendedor;


    public NotaVenta(BigDecimal IVA, LocalDate fechaCreacion, LocalDate vencimiento,
                     Comprobante comprobante, BigDecimal tipoCambio, BigDecimal interesMensual,
                     String formaDePago) {
        this.IVA = IVA;
        this.fechaCreacion = fechaCreacion;
        this.vencimiento = vencimiento;
        this.comprobante = comprobante;
        this.tipoCambio = tipoCambio;
        this.interesMensual = interesMensual;
        this.formaDePago = formaDePago;

    }

    public long calcularMeses() {
        LocalDate fechaInicio = this.fechaCreacion.withDayOfMonth(1); // Ajustar al primer día del mes
        LocalDate fechaFin = this.vencimiento.withDayOfMonth(1); // Ajustar al primer día del mes

        return ChronoUnit.MONTHS.between(fechaInicio, fechaFin);
    }

    public BigDecimal calcularTotalConIntereses() {
        long meses = calcularMeses();
        if (meses > 0) {
            BigDecimal interesMensual = this.interesMensual.divide(BigDecimal.valueOf(100));
            BigDecimal interesTotal = this.totalUSD.multiply(interesMensual).multiply(BigDecimal.valueOf(meses));
            return this.totalUSD.add(interesTotal);
        } else {
            return this.totalUSD;
        }
    }

    public BigDecimal calcularTotalIVA() {
        this.totalIVA = totalUSD.multiply(this.IVA.divide(BigDecimal.valueOf(100)));
        return totalIVA;
    }

    public BigDecimal calcularSubTotalUSD() {
        BigDecimal subTotalUSD = new BigDecimal(0.0);
        for (DetalleNotaVenta detalle : this.detalles) {
            subTotalUSD = subTotalUSD.add(detalle.getSubtotalVenta());
        }
        System.out.println("subtotal: " + subTotalUSD);
        return subTotalUSD;
    }

    public BigDecimal calcularTotalUSD() {
        BigDecimal totalUSD = calcularSubTotalUSD();

        if (IVA.compareTo(BigDecimal.ZERO) > 0) {
            // Si hay IVA, aplica el IVA al totalUSD basado en el precio de venta
            totalUSD = totalUSD.multiply(BigDecimal.ONE.add(IVA.divide(BigDecimal.valueOf(100))));
        } else {
            // Si el IVA es 0, aplica el interés sobre el precio costo
//            BigDecimal totalCostoInteres = BigDecimal.ZERO;
//
//            for (DetalleNotaVenta detalle : detalles) {
//                BigDecimal interes = detalle.getPrecioCosto()
//                        .multiply(interesMensual)
//                        .multiply(BigDecimal.valueOf(calcularMeses()))
//                        .divide(BigDecimal.valueOf(100));
//                totalCostoInteres = totalCostoInteres.add(interes.multiply(detalle.getCantidad()));
//                System.out.println("totalCostoIntereses" +totalCostoInteres);
//            }
//            totalUSD = totalUSD.add(totalCostoInteres);
//            System.out.println("total USD" + totalUSD);
//        }

            BigDecimal tasaInteres = BigDecimal.ONE.add(interesMensual.multiply(BigDecimal.valueOf(calcularMeses()))
                    .divide(BigDecimal.valueOf(100)));
            totalUSD = totalUSD.multiply(tasaInteres);
        }
            return totalUSD;

    }

        public BigDecimal calcularTotalARS () {
            return this.totalARS = this.totalUSD.multiply(this.tipoCambio);
        }


        @Override
        public String toString () {
            return "NotaVenta{" +
                    "id=" + id +
                    ", IVA=" + IVA +
                    ", fechaCreacion=" + fechaCreacion +
                    ", vencimiento=" + vencimiento +
                    ", comprobante=" + comprobante +
                    ", tipoCambio=" + tipoCambio +
                    ", interesMensual=" + interesMensual +
                    ", formaDePago='" + formaDePago + '\'' +
                    ", totalUSD=" + totalUSD +
                    ", subtotalUSD=" + subtotalUSD +
                    ", totalARS=" + totalARS +
                    ", estado=" + estado +
                    ", cliente=" + cliente.getId() +
                    ", vendedor=" + vendedor.getId() +
                    '}';
        }
    }


