package com.andres.notaVenta;

import com.andres.notaVenta.entities.Sucursal;
import com.andres.notaVenta.entities.Vendedor;
import com.andres.notaVenta.repositories.RoleRepository;
import com.andres.notaVenta.repositories.AppUserRepository;

import com.andres.notaVenta.repositories.VendedorRepository;
import com.andres.notaVenta.security.AppUser;
import com.andres.notaVenta.security.Role;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Set;

@SpringBootApplication
public class NotaVentaApplication {

	public static void main(String[] args) {
		SpringApplication.run(NotaVentaApplication.class, args);


	}
	@Component
	public class DataInitializer {

		@Bean
		public CommandLineRunner initData(AppUserRepository appUserRepository, RoleRepository roleRepository, VendedorRepository vendedorRepository, BCryptPasswordEncoder passwordEncoder) {
			return args -> {

				Role adminRole = roleRepository.findByName("ROLE_ADMIN");
				if (adminRole == null) {
					adminRole = new Role("ROLE_ADMIN");
					roleRepository.save(adminRole);
				}

				Role vendedorRole = roleRepository.findByName("ROLE_VENDEDOR");
				if (vendedorRole == null) {
					vendedorRole = new Role("ROLE_VENDEDOR");
					roleRepository.save(vendedorRole);
				}

				// Crear usuario Admin con contraseña cifrada
				if (appUserRepository.findByUsername("admin") == null) {
					AppUser admin = new AppUser();
					admin.setUsername("admin");
					admin.setPassword(passwordEncoder.encode("admin123"));
					admin.setEnabled(false);
					admin.setRoles(Set.of(adminRole));
					appUserRepository.save(admin);
				}

				// Crear usuario Vendedor con contraseña cifrada
				if (appUserRepository.findByUsername("vendedor") == null) {
					AppUser vendedor = new AppUser();
					vendedor.setUsername("vendedor");
					vendedor.setPassword(passwordEncoder.encode("vendedor123"));
					vendedor.setEnabled(false);
					vendedor.setRoles(Set.of(vendedorRole));
					appUserRepository.save(vendedor);
					Vendedor vendedor1 = new Vendedor();
					vendedor1.setSucursal(Sucursal.MATORRALES);
					vendedor1.setNombre("vendedorPrueba");
					vendedor1.setAppUser(vendedor);
					vendedorRepository.save(vendedor1);
				}





			};
		}
	}

}
