package com.andres.notaVenta.controller.admin;

import com.andres.notaVenta.entities.NotaVenta;
import com.andres.notaVenta.services.NotaVentaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/admin/notasVentas")
public class NotaVentaAdminController {

    @Autowired
    NotaVentaService notaVentaService;

    @GetMapping
    public String listar(
            Model model,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("fechaCreacion").descending());
        Page<NotaVenta> notasVentaPage = notaVentaService.listar(pageable);

        model.addAttribute("notasVentaPage", notasVentaPage);
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
