package com.andres.notaVenta.services;

import com.andres.notaVenta.entities.Cliente;
import com.andres.notaVenta.entities.Vendedor;
import com.andres.notaVenta.repositories.VendedorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VendedorService {

    @Autowired
    VendedorRepository vendedorRepository;


    public List<Vendedor> listarTodos(){
        return vendedorRepository.findAll();
    }

    public void eliminarPorId(Long id) {
        vendedorRepository.deleteById(id);
    }

    public void guardar(Vendedor vendedor) {
        vendedorRepository.save(vendedor);
    }
}
