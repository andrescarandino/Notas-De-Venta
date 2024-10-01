package com.andres.notaVenta.Dtos;

import com.andres.notaVenta.repositories.AppUserRepository;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class VendedorForm {
    private String nombre;
    private String username;
    private String password;
    private String verifyPassword;

    public boolean passwordConfirm(){
        if (this.password.equals(this.verifyPassword)){
            return true;
        }
        return false;
    }

}
