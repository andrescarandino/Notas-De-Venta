package com.andres.notaVenta.Exceptions;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.sql.SQLIntegrityConstraintViolationException;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(DataIntegrityViolationException.class)
    public String handleDataIntegrityViolationException(DataIntegrityViolationException ex,
                                                        RedirectAttributes redirectAttributes) {

        String customMessage = determineCustomErrorMessage(ex);
        System.out.println(customMessage);
        redirectAttributes.addFlashAttribute("mensajeError", customMessage);
        return determineRedirectUrl(ex);
    }

    private String determineCustomErrorMessage(DataIntegrityViolationException ex) {
        String errorMessage = "No se puede eliminar este registro.";

        if (ex.getMessage().contains("FOREIGN KEY (`vendedor_id`)")) {
            errorMessage = "No es posible eliminar el vendedor";
        } else if (ex.getMessage().contains("FOREIGN KEY (`producto_id`)")) {
            errorMessage = "No es posible eliminar el producto";
        } else if (ex.getMessage().contains("FOREIGN KEY (`cliente_id`)")) {
            errorMessage = "No es posible eliminar el cliente";
        }

        return errorMessage;
    }

    private String determineRedirectUrl(DataIntegrityViolationException ex) {
        String redirectUrl = "/"; // Ruta por defecto (inicio)

        if (ex.getMessage().contains("FOREIGN KEY (`vendedor_id`)")) {
            redirectUrl = "redirect:/admin/vendedores"; // Redirigir a la página de vendedores
        } else if (ex.getMessage().contains("FOREIGN KEY (`producto_id`)")) {
            redirectUrl = "redirect:/productos/create"; // Redirigir a la página de productos
        } else if (ex.getMessage().contains("FOREIGN KEY (`cliente_id`)")) {
            redirectUrl = "redirect:/vendedor/clientes"; // Redirigir a la página de clientes
        }

        return redirectUrl;
    }
}

