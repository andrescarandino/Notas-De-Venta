package com.andres.notaVenta.controller;

import com.andres.notaVenta.entities.Cliente;
import com.andres.notaVenta.entities.NotaVenta;
import com.andres.notaVenta.entities.Producto;
import com.andres.notaVenta.entities.Vendedor;
import com.andres.notaVenta.services.ClienteService;
import com.andres.notaVenta.services.NotaVentaService;
import com.andres.notaVenta.services.VendedorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/notasVentas")
public class NotaVentaController {

    @Autowired
    NotaVentaService notaVentaService;
    @Autowired
    ClienteService clienteService;
    @Autowired
    VendedorService vendedorService;

    @GetMapping("/crearNotaVenta")
    public String mostrarFormulario(Model model) {
        NotaVenta notaVenta = new NotaVenta();
        List<Producto> productos = new ArrayList<>();
        List<Cliente> clientes =   clienteService.listarTodos();
        List<Vendedor> vendedores = vendedorService.listarTodos();

        model.addAttribute("notaVenta", notaVenta);
        model.addAttribute("productos", productos);
        model.addAttribute("clientes", clientes);
        model.addAttribute("vendedores", vendedores);

        return "index";
    }

    @PostMapping("/save")
    public String saveNotaVenta(@ModelAttribute NotaVenta notaVenta) {
        notaVentaService.saveNotaVenta(notaVenta);
        return "redirect:/notasVentas/listar";
    }

    @GetMapping("/listar")
    public String listarNotasVentas(Model model) {
        List<NotaVenta> notasVenta = notaVentaService.listar();
        System.out.println(notasVenta);
        model.addAttribute("notasVenta", notasVenta);
        return "listarNotasVentas";
    }


    @GetMapping("/detalle/{id}")
    public String mostrarNotaVenta(@PathVariable Long id, Model model) {
        NotaVenta notaVenta = notaVentaService.obtenerNotaVentaPorId(id);
        model.addAttribute("notaVenta", notaVenta);
        return "notaVentaAlDetalle";
    }

    @PostMapping("/delete/{id}")
    public String eliminarNotaVenta(@PathVariable Long id){
        notaVentaService.eliminarPorId(id);
        return "redirect:/notasVentas/listar";
    }





}
