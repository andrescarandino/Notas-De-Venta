package com.andres.notaVenta.controller;

import com.andres.notaVenta.entities.NotaVenta;
import com.andres.notaVenta.entities.Producto;
import com.andres.notaVenta.services.ProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/productos")
public class ProductosController {

    @Autowired
    ProductoService productoService;

    @GetMapping("/create")
    public String showCreateForm(Model model) {
        List<Producto> productos = productoService.listarTodos();
        model.addAttribute("producto", new Producto());
        model.addAttribute("listaProductos", productos);
        return "productoForm"; // Nombre del archivo Thymeleaf sin la extensión
    }

    @PostMapping("/delete/{id}")
    public String eliminarProducto(@PathVariable Long id){
        productoService.eliminarPorId(id);
        return "redirect:/productos/create";
    }

    @PostMapping("/save")
    public String saveNotaVenta(@ModelAttribute Producto producto) {
        productoService.guardar(producto);
        return "redirect:/productos/create";
    }


}
