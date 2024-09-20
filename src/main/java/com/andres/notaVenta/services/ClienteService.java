package com.andres.notaVenta.services;

import com.andres.notaVenta.entities.Cliente;
import com.andres.notaVenta.entities.Producto;
import com.andres.notaVenta.repositories.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClienteService {

    @Autowired
    ClienteRepository clienteRepository;

    public List<Cliente> listarTodos(){
        return clienteRepository.findAll();
    }

    public void eliminarPorId(Long id) {
        clienteRepository.deleteById(id);
    }

    public void guardar(Cliente cliente) {
        clienteRepository.save(cliente);
    }

    // Método para buscar clientes por el username del vendedor
    public List<Cliente> findByVendedorUsername(String username) {
        return clienteRepository.findByVendedor_Nombre(username);
    }
}
