package com.andres.notaVenta.services;

import com.andres.notaVenta.entities.Cliente;
import com.andres.notaVenta.entities.Producto;
import com.andres.notaVenta.entities.Vendedor;
import com.andres.notaVenta.repositories.ClienteRepository;
import com.andres.notaVenta.repositories.VendedorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ClienteService {

    @Autowired
    ClienteRepository clienteRepository;

    @Autowired
    VendedorRepository vendedorRepository;


    public List<Cliente> listarTodos(){
        return clienteRepository.findAll();
    }

    public void eliminarPorId(Long id) {
        clienteRepository.deleteById(id);
    }

    public void guardar(Cliente cliente, String username) {
        Optional<Vendedor> vendedor = vendedorRepository.findByAppUserUsername(username);
        if (vendedor.isPresent()){
            cliente.setVendedor(vendedor.get());
        }
        clienteRepository.save(cliente);
        System.out.println(cliente);;
    }


    public List<Cliente> ListarClientesPorVendedor(String username) {
        String nombre = "";
        Optional<Vendedor> vendedor = vendedorRepository.findByAppUserUsername(username);
        if (vendedor.isPresent()){
            nombre = vendedor.get().getNombre();
        }
        return clienteRepository.findByVendedor_Nombre(nombre);
    }
}
