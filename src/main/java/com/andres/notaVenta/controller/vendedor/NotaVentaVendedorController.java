package com.andres.notaVenta.controller.vendedor;

import com.andres.notaVenta.entities.Cliente;
import com.andres.notaVenta.entities.NotaVenta;
import com.andres.notaVenta.entities.Producto;
import com.andres.notaVenta.entities.Vendedor;
import com.andres.notaVenta.services.ClienteService;
import com.andres.notaVenta.services.NotaVentaService;
import com.andres.notaVenta.services.VendedorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/vendedor/notasVentas")
public class NotaVentaVendedorController {

    @Autowired
    NotaVentaService notaVentaService;
    @Autowired
    VendedorService vendedorService;
    @Autowired
    ClienteService clienteService;

    @GetMapping
    public String listar(Model model, Authentication auth) {
        String username = auth.getName();
        List<NotaVenta> notasVenta = notaVentaService.findByVendedorUsername(username);
        model.addAttribute("notasVenta", notasVenta);
        return "vendedores/notasVentasVendedor";
    }

    @GetMapping("/crear")
    public String mostrarFormulario(Model model, Authentication authentication) {
        NotaVenta notaVenta = new NotaVenta();
        List<Producto> productos = new ArrayList<>();
        String username = authentication.getName();
        List<Cliente> clientes =   clienteService.ListarClientesPorVendedor(username);

        model.addAttribute("notaVenta", notaVenta);
        model.addAttribute("productos", productos);
        model.addAttribute("clientes", clientes);

        return "vendedores/notaVentaForm";
    }

    @PostMapping("/guardar")
    public String saveNotaVenta(@ModelAttribute NotaVenta notaVenta, Authentication auth) {
        Vendedor vendedor = vendedorService.obtenerVendedorPorUsername(auth.getName());
        notaVenta.setVendedor(vendedor);
        notaVentaService.saveNotaVenta(notaVenta);
        return "redirect:/vendedor/notasVentas";
    }

    @GetMapping("/detalle/{id}")
    public String mostrarNotaVenta(@PathVariable Long id, Model model) {
        NotaVenta notaVenta = notaVentaService.obtenerNotaVentaPorId(id);
        model.addAttribute("notaVenta", notaVenta);
        return "vendedores/DetalleVendedor";
    }

    @PostMapping("/delete/{id}")
    public String eliminarNotaVenta(@PathVariable Long id){
        notaVentaService.eliminarPorId(id);
        return "redirect:/vendedor/notasVentas";
    }



}
