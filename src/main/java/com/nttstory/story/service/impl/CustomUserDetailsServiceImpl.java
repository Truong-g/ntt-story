package com.nttstory.story.service.impl;

import com.nttstory.story.model.User;
import com.nttstory.story.repository.UserRepository;
import com.nttstory.story.util.UserDetailsCustom;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@Service
public class CustomUserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserDetailsCustom userDetailsCustom = getUserDetaisCustom(username);
        return userDetailsCustom;
    }


    private UserDetailsCustom getUserDetaisCustom(String email) {
        User user = userRepository.findByEmail(email).orElseThrow();
        Set<GrantedAuthority> authorities = user.getRoles().stream().map(role -> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toSet());
        return new UserDetailsCustom(
                user.getEmail(),
                user.getPassword(),
                authorities
                );
    }
}
