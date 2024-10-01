package com.andres.notaVenta.controller.admin;

import com.andres.notaVenta.Dtos.VendedorForm;
import com.andres.notaVenta.entities.Vendedor;
import com.andres.notaVenta.services.VendedorService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/admin/vendedores")
public class VendedorAdminController {

    @Autowired
    private VendedorService vendedorService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping("/crear")
    public String mostrarFormulario(Model model) {
        model.addAttribute("vendedorForm", new VendedorForm());
        return "admin/vendedorForm";
    }

    @PostMapping("/guardar")
    public String registrarVendedor(@Valid @ModelAttribute("vendedorForm") VendedorForm vendedorForm, RedirectAttributes redirectAttributes, BindingResult result) {
        if(!vendedorForm.passwordConfirm()){
            result.rejectValue("verifyPassword", "error.verifyPassword", "Las contraseñas no coinciden");
        }
        if (result.hasErrors()){
            return "/admin/vendedorForm";
        }
        vendedorService.registrarVendedor(vendedorForm, passwordEncoder);
        return "redirect:/admin/vendedores";
    }

    @GetMapping
    public String listarVendedores(Model model){
        List<Vendedor> vendedores = vendedorService.listarTodos();
        System.out.println(vendedores);
        model.addAttribute("vendedores", vendedores);
        return "admin/listaVendedores";
    }

    @PostMapping("/eliminar/{id}")
    public String eliminarVendedor(@PathVariable Long id){
        vendedorService.eliminarPorId(id);
        return "redirect:/admin/vendedores";
    }


}
