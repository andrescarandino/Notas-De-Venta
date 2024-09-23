package com.andres.notaVenta.controller.vendedor;

import com.andres.notaVenta.entities.Cliente;

import com.andres.notaVenta.services.ClienteService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("vendedor/clientes")
public class ClienteVendedorController {
    @Autowired
    ClienteService clienteService;


    @GetMapping("/crear")
    public String mostrarFormulario(Model model) {
        model.addAttribute("cliente", new Cliente());
        return "vendedores/clientesForm";
    }


    @PostMapping("/guardar")
    public String guardarCliente(@ModelAttribute Cliente cliente, Authentication authentication){
        String username = authentication.getName();
        clienteService.guardar(cliente, username);
        return "redirect:/vendedor/clientes";
    }

    @GetMapping
    public String listarClientes(Model model, Authentication authentication) {
        String username = authentication.getName();
        List<Cliente> clientes = clienteService.ListarClientesPorVendedor(username);
        System.out.println(clientes);
        model.addAttribute("clientes", clientes);
        return "vendedores/listaClientesPorVendedor";
    }

    @PostMapping("eliminar/{id}")
    public String eliminarCliente(@PathVariable Long id){
        clienteService.eliminarPorId(id);
        return "redirect:/vendedor/clientes";
    }

}
