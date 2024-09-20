package com.andres.notaVenta.controller;

import com.andres.notaVenta.entities.Cliente;
import com.andres.notaVenta.entities.Producto;
import com.andres.notaVenta.services.ClienteService;
import com.andres.notaVenta.services.ProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/clientes")
public class ClienteController {

    @Autowired
    ClienteService clienteService;

    @GetMapping("/create")
    public String showCreateForm(Model model) {
        List<Cliente> clientes = clienteService.listarTodos();
        model.addAttribute("cliente", new Cliente());
        model.addAttribute("listaClientes", clientes);
        return "clienteForm"; // Nombre del archivo Thymeleaf sin la extensión
    }

    @PostMapping("/delete/{id}")
    public String eliminarCliente(@PathVariable Long id){
        clienteService.eliminarPorId(id);
        return "redirect:/clientes/create";
    }

    @PostMapping("/save")
        public String guardarCliente(@ModelAttribute Cliente cliente) {
        clienteService.guardar(cliente);
        return "redirect:/clientes/create";
    }

    // El administrador puede ver todos los clientes
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/listar")
    public String listarClientes(Model model) {
        List<Cliente> clientes = clienteService.listarTodos();
        model.addAttribute("clientes", clientes);
        return "clientes/listar";
    }

    // Los vendedores solo pueden ver sus propios clientes
    @PreAuthorize("hasRole('VENDEDOR')")
    @GetMapping("/listarVendedor")
    public String listarClientesVendedor(Model model, Authentication authentication) {
        String username = authentication.getName();
        List<Cliente> clientes = clienteService.findByVendedorUsername(username);
        model.addAttribute("clientes", clientes);
        return "listaClientesPorVendedor";
    }

}
