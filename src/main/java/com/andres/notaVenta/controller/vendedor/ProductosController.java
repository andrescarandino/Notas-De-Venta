package com.andres.notaVenta.controller.vendedor;

import com.andres.notaVenta.entities.Producto;
import com.andres.notaVenta.services.ProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/vendedor/productos")
public class ProductosController {

    @Autowired
    ProductoService productoService;

    @GetMapping("/crear")
    public String showCreateForm(Model model) {
        List<Producto> productos = productoService.listarTodos();
        model.addAttribute("producto", new Producto());
        model.addAttribute("listaProductos", productos);
        return "vendedores/producto_formulario";
    }

    @PostMapping("/eliminar/{id}")
    public String eliminarProducto(@PathVariable Long id){
        productoService.eliminarPorId(id);
        return "redirect:/vendedor/productos/crear";
    }

    @PostMapping("/guardar")
    public String saveNotaVenta(@ModelAttribute Producto producto) {
        productoService.guardar(producto);
        return "redirect:/vendedor/productos/crear";
    }


}
