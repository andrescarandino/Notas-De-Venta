package com.andres.notaVenta.entities;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;


public enum Comprobante {
    FACTURA, NOTADEBITO, NOTACREDITO, DEBITOINTERNO;
}
