package com.andres.notaVenta.controller;

import com.andres.notaVenta.entities.Cliente;
import com.andres.notaVenta.entities.Vendedor;
import com.andres.notaVenta.services.ClienteService;
import com.andres.notaVenta.services.VendedorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/vendedores")
public class VendedorController {

    @Autowired
    VendedorService vendedorService;

    @GetMapping("/create")
    public String showCreateForm(Model model) {
        List<Vendedor> vendedores = vendedorService.listarTodos();
        model.addAttribute("vendedor", new Vendedor());
        model.addAttribute("listaVendedores", vendedores);
        return "vendedorForm"; // Nombre del archivo Thymeleaf sin la extensión
    }

    @PostMapping("/delete/{id}")
    public String eliminarVendedor(@PathVariable Long id){
        vendedorService.eliminarPorId(id);
        return "redirect:/vendedores/create";
    }

    @PostMapping("/save")
    public String guardarVendedor(@ModelAttribute Vendedor vendedor) {
        vendedorService.guardar(vendedor);
        return "redirect:/vendedores/create";
    }
}
