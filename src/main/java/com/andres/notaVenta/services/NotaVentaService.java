package com.andres.notaVenta.services;

import com.andres.notaVenta.entities.*;
import com.andres.notaVenta.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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


        notaVenta.setCliente(cliente);
        notaVenta.setVendedor(vendedor);

        for (DetalleNotaVenta detalle : notaVenta.getDetalles()){
            Long productoId = detalle.getProducto().getId();
            System.out.println("Buscando producto con ID: " + productoId);
            Producto producto = productoRepository.findById(detalle.getProducto().getId()).orElseThrow();
            detalle.setProducto(producto);
            detalle.setNombre(producto.getNombre());
            detalle.setPrecioCosto(producto.getPrecioCosto());
            detalle.setSubtotalCosto(detalle.calcularSubtotalCosto());
            detalle.setPrecioVenta(detalle.calcularPrecioVenta());
            detalle.setSubtotalVenta(detalle.calcularSubtotalVenta());
            detalle.setNotaVenta(notaVenta);
            System.out.println(detalle);
        }
        notaVenta.setEstado(Estado.EN_ESPERA);

        notaVenta.setSubtotalUSD(notaVenta.calcularSubTotalUSD());
        notaVenta.setTotalUSD(notaVenta.calcularTotalUSD());
        notaVenta.setTotalIVA(notaVenta.calcularTotalIVA());
        notaVenta.setTotalARS(notaVenta.calcularTotalARS());


        notaVentaRepository.save(notaVenta);


        System.out.println(notaVenta);
    }

    public NotaVenta obtenerNotaVentaPorId(Long id) {
        return notaVentaRepository.findById(id).orElseThrow();
    }

    public List<NotaVenta> listar() {
        return notaVentaRepository.findAll();
    }

    public void eliminarPorId(Long id) {
        notaVentaRepository.deleteById(id);
    }
}
