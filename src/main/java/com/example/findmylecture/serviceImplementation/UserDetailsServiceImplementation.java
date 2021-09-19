package com.example.findmylecture.serviceImplementation;

import com.example.findmylecture.authentication.UserAuthentication;
import com.example.findmylecture.model.User;
import com.example.findmylecture.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Locale;

@Service
public class UserDetailsServiceImplementation implements UserDetailsService {

    @Autowired
    private UserRepo userRepo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepo.findUserByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException(String.format("%s not found", username));
        } else {
            ArrayList<GrantedAuthority> grantedAuthorities = new ArrayList<>();
            grantedAuthorities.add(new SimpleGrantedAuthority(user.getRole().getRoleId().toString().toUpperCase(Locale.ROOT)));
            return new UserAuthentication(grantedAuthorities, user.getUsername(), user.getPassword(), true, true, true, true);
        }
    }
}