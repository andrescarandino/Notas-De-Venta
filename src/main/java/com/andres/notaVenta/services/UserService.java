package com.andres.notaVenta.services;

import com.andres.notaVenta.repositories.AppUserRepository;
import com.andres.notaVenta.security.AppUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    AppUserRepository appUserRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException{
        AppUser appUser = appUserRepository.findByUsername(username);
        if (appUser != null){
            var SpringUser = User.withUsername(appUser.getUsername())
                    .password(appUser.getPassword())
                    .authorities(appUser.getAuthorities())
                    .accountLocked(appUser.isEnabled())
                    .build();
            System.out.println(SpringUser);
            return SpringUser;
        }
        return null;
    }

    public void saveUser(AppUser appUser, PasswordEncoder passwordEncoder) {
        appUser.setPassword(passwordEncoder.encode(appUser.getPassword()));
        appUserRepository.save(appUser);
    }
}
