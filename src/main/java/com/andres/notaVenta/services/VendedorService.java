package com.andres.notaVenta.services;

import com.andres.notaVenta.Dtos.VendedorForm;
import com.andres.notaVenta.entities.Cliente;
import com.andres.notaVenta.entities.Vendedor;
import com.andres.notaVenta.repositories.AppUserRepository;
import com.andres.notaVenta.repositories.RoleRepository;
import com.andres.notaVenta.repositories.VendedorRepository;
import com.andres.notaVenta.security.AppUser;
import com.andres.notaVenta.security.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class VendedorService {
    @Autowired
    private AppUserRepository appUserRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    VendedorRepository vendedorRepository;


    public List<Vendedor> listarTodos(){
        return vendedorRepository.findAllVendedoresWithAppUser();
    }

    public void eliminarPorId(Long id) {
        vendedorRepository.deleteById(id);
    }


    public void registrarVendedor(VendedorForm vendedorForm, PasswordEncoder passwordEncoder) {
        // Crear usuario
        AppUser appUser = new AppUser();
        appUser.setUsername(vendedorForm.getUsername());
        appUser.setPassword(passwordEncoder.encode(vendedorForm.getPassword()));
        appUser.setEnabled(false);

        // Asignar rol VENDEDOR
        Role vendedorRole = roleRepository.findByName("VENDEDOR");
        appUser.getRoles().add(vendedorRole);
        appUserRepository.save(appUser);

        // Crear Vendedor
        Vendedor vendedor = new Vendedor();
        vendedor.setNombre(vendedorForm.getNombre());
        vendedor.setAppUser(appUser);
        vendedorRepository.save(vendedor);
    }

    public Vendedor obtenerVendedorPorUsername(String userName){
        Vendedor vendedor = vendedorRepository.findByAppUserUsername(userName).orElseThrow();
        System.out.println(vendedor);
        return vendedor;
    }


}
