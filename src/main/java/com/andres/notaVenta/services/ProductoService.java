package com.andres.notaVenta.services;

import com.andres.notaVenta.entities.Producto;
import com.andres.notaVenta.repositories.ProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductoService {
    @Autowired
    private ProductoRepository productoRepository;

    public List<Producto> buscarPorNombre(String nombre) {
        return productoRepository.findByNombreContainingIgnoreCase(nombre);
    }

    public List<Producto> listarTodos(){
        return productoRepository.findAll();
    }

    public void eliminarPorId(Long id) {
        productoRepository.deleteById(id);
    }

    public void guardar(Producto producto) {
        productoRepository.save(producto);
    }

    public Producto buscarPorId(Long id) {
        return productoRepository.findById(id).orElseThrow();
    }
}
