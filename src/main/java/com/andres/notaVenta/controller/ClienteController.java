package com.andres.notaVenta.controller;

import com.andres.notaVenta.entities.Cliente;
import com.andres.notaVenta.entities.Producto;
import com.andres.notaVenta.services.ClienteService;
import com.andres.notaVenta.services.ProductoService;
import org.springframework.beans.factory.annotation.Autowired;
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
}
