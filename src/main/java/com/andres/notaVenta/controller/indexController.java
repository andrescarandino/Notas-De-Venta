package com.andres.notaVenta.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class indexController {

    @GetMapping()
    public String paginaInicio() {
        return "redirect:/notasVentas/crearNotaVenta";
    }
}
