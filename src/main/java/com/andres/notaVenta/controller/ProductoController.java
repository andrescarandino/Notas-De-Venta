package com.andres.notaVenta.controller;

import com.andres.notaVenta.Dtos.ProductoDto;
import com.andres.notaVenta.entities.Producto;
import com.andres.notaVenta.repositories.ProductoRepository;
import com.andres.notaVenta.services.ProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;
@RestController
@RequestMapping("/productos")
public class ProductoController {

    @Autowired
    ProductoService productoService;


    @GetMapping("/buscar")
    public ResponseEntity<List<ProductoDto>> buscarProductos(@RequestParam("nombre") String nombre) {
        List<Producto> productos = productoService.buscarPorNombre(nombre);
        List<ProductoDto> productosDTO = productos.stream()
                .map(p -> new ProductoDto(p.getId(), p.getNombre()))
                .collect(Collectors.toList());
        return ResponseEntity.ok(productosDTO);
    }

}
