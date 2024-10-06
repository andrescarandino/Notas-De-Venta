package com.andres.notaVenta.controller.vendedor;

import com.andres.notaVenta.entities.Cliente;
import com.andres.notaVenta.entities.NotaVenta;
import com.andres.notaVenta.entities.Producto;
import com.andres.notaVenta.entities.Vendedor;
import com.andres.notaVenta.services.ClienteService;
import com.andres.notaVenta.services.NotaVentaService;
import com.andres.notaVenta.services.VendedorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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
    public String listar(Model model, Authentication auth,
                         @RequestParam (defaultValue = "0") int page,
                         @RequestParam (defaultValue = "10") int size) {
        String username = auth.getName();
        Pageable pageable = PageRequest.of(page, size, Sort.by("fechaCreacion").descending());
        Page<NotaVenta> notasVenta = notaVentaService.listaNotasVentasPorVendedor(username, pageable);
        System.out.println(notasVenta);
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
