package com.andres.notaVenta.services;

import com.andres.notaVenta.entities.*;
import com.andres.notaVenta.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

@Service
public class NotaVentaService {

    @Autowired
    NotaVentaRepository notaVentaRepository;
    @Autowired
    ClienteRepository clienteRepository;
    @Autowired
    VendedorRepository vendedorRepository;
    @Autowired
    ProductoRepository productoRepository;
    @Autowired
    DetalleNotaVentaRepository detalleNotaVentaRepository;

    @Transactional
    public void saveNotaVenta(NotaVenta notaVenta) {
        Cliente cliente = clienteRepository.findById(notaVenta.getCliente().getId()).orElseThrow();
        Vendedor vendedor = vendedorRepository.findById(notaVenta.getVendedor().getId()).orElseThrow();


        notaVenta.setFechaCreacion(LocalDateTime.now());
        notaVenta.setCliente(cliente);
        notaVenta.setVendedor(vendedor);

        for (DetalleNotaVenta detalle : notaVenta.getDetalles()){
            Producto producto = productoRepository.findById(detalle.getProducto().getId()).orElseThrow();
            detalle.setProducto(producto);
            if(!notaVenta.getConIVA()){
                detalle.setIVA(BigDecimal.ZERO);
            }
            detalle.setNombre(producto.getNombre());
            detalle.setPrecioCosto(detalle.getPrecioCosto());
            detalle.setSubtotalCosto(detalle.calcularSubtotalCosto());
            detalle.setPrecioVenta(detalle.calcularPrecioVenta());
            detalle.setSubtotalVenta(detalle.calcularSubtotalVenta());
            detalle.setNotaVenta(notaVenta);
        }
        notaVenta.setEstado(Estado.EN_ESPERA);
        if(notaVenta.getConIVA()){
            notaVenta.setTotalIVAVeintiUno(notaVenta.calcularIVAVeintiUno());
            notaVenta.setTotalIVADiezCinco(notaVenta.calcularIVADiezCinco());
        }else {
            notaVenta.setTotalIVAVeintiUno(BigDecimal.ZERO);
            notaVenta.setTotalIVADiezCinco(BigDecimal.ZERO);
        }



        notaVenta.setSubtotalUSD(notaVenta.calcularSubTotalUSD());
        notaVenta.setTotalUSD(notaVenta.calcularTotalUSD());
        notaVentaRepository.save(notaVenta);

    }

    public NotaVenta obtenerNotaVentaPorId(Long id) {
        return notaVentaRepository.findById(id).orElseThrow();
    }

    public Page<NotaVenta> listar(Pageable pageable) {
        return notaVentaRepository.findAll(pageable);
    }

    public void eliminarPorId(Long id) {
        notaVentaRepository.deleteById(id);
    }

    public Page<NotaVenta> listaNotasVentasPorVendedor(String username, Pageable pageable) {
        Optional<Vendedor> vendedor = vendedorRepository.findByAppUserUsernameIgnoreCase(username);
        Page<NotaVenta> notasVentas = null;
        if (vendedor.isPresent()){
            notasVentas = notaVentaRepository.findByVendedorNombre(vendedor.get().getNombre(), pageable);
        }

        return notasVentas;
    }
}
