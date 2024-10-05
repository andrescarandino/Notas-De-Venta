package com.andres.notaVenta.entities;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDate;
import java.time.LocalDateTime;
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
    private Boolean conIVA;
    private LocalDateTime fechaCreacion;
    private LocalDate vencimiento;
    @Enumerated(EnumType.STRING)
    private Comprobante comprobante;
    private BigDecimal tipoCambio;
    private BigDecimal interesMensual;
    @Enumerated(EnumType.STRING)
    private FormaDePago formaDePago;
    private String observaciones;
    private BigDecimal totalIVAVeintiUno;
    private BigDecimal totalIVADiezCinco;


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


    public NotaVenta( LocalDateTime fechaCreacion, LocalDate vencimiento,
                     Comprobante comprobante, BigDecimal tipoCambio, BigDecimal interesMensual,
                     FormaDePago formaDePago, String observaciones) {

        this.fechaCreacion = fechaCreacion;
        this.vencimiento = vencimiento;
        this.comprobante = comprobante;
        this.tipoCambio = tipoCambio;
        this.interesMensual = interesMensual;
        this.formaDePago = formaDePago;
        this.observaciones = observaciones;
    }

    public long calcularMeses() {
        LocalDate fechaInicio = this.fechaCreacion.toLocalDate().withDayOfMonth(1);//this.fechaCreacion.withDayOfMonth(1); // Ajustar al primer día del mes
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

    public BigDecimal calcularIVAVeintiUno() {
        BigDecimal ivaVentiuno= new BigDecimal(0.0);
        for (DetalleNotaVenta detalle : this.detalles) {
            if (detalle.getIVA().compareTo(BigDecimal.valueOf(21)) == 0)
            ivaVentiuno = ivaVentiuno.add(detalle.calcularIVADetalle());
        }
        return ivaVentiuno;
    }

    public BigDecimal calcularIVADiezCinco() {
        BigDecimal IVADiezCinco= new BigDecimal(0.0);
        for (DetalleNotaVenta detalle : this.detalles) {
            if (detalle.getIVA().compareTo(BigDecimal.valueOf(10.5)) == 0)
                IVADiezCinco = IVADiezCinco.add(detalle.calcularIVADetalle());
        }
        return IVADiezCinco;
    }

    public BigDecimal calcularSubTotalUSD() {
        BigDecimal subTotalUSD = new BigDecimal(0.0);
        for (DetalleNotaVenta detalle : this.detalles) {
            subTotalUSD = subTotalUSD.add(detalle.getSubtotalVenta());
        }
        return subTotalUSD;
    }

    public BigDecimal calcularTotalUSD() {
        BigDecimal totalUSD = calcularSubTotalUSD();

        if (conIVA) {
            // Si hay IVA, aplica el IVA al totalUSD basado en el precio de venta
            //totalUSD = totalUSD.multiply(BigDecimal.ONE.add(IVA.divide(BigDecimal.valueOf(100))));
            for (DetalleNotaVenta detalle : this.detalles) {
                totalUSD = totalUSD.add(detalle.calcularIVADetalle());
            }
        } else {
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
                    ", vendedor=" +vendedor.getId() +
                    '}';
        }
    }


