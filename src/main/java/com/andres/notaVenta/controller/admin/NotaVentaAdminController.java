package com.andres.notaVenta.controller.admin;

import com.andres.notaVenta.entities.NotaVenta;
import com.andres.notaVenta.services.NotaVentaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/admin/notasVentas")
public class NotaVentaAdminController {

    @Autowired
    NotaVentaService notaVentaService;

    @GetMapping
    public String listarNotasVentas(Model model) {
        List<NotaVenta> notasVenta = notaVentaService.listar();
        System.out.println(notasVenta);
        model.addAttribute("notasVenta", notasVenta);
        return "admin/listaNotasVentas";
    }

    @GetMapping("/detalle/{id}")
    public String mostrarNotaVenta(@PathVariable Long id, Model model) {
        NotaVenta notaVenta = notaVentaService.obtenerNotaVentaPorId(id);
        model.addAttribute("notaVenta", notaVenta);
        return "admin/DetalleAdmin";
    }

    @PostMapping("/eliminar/{id}")
    public String eliminarNotaVenta(@PathVariable Long id){
        notaVentaService.eliminarPorId(id);
        return "redirect:/admin/notasVentas";
    }
}
