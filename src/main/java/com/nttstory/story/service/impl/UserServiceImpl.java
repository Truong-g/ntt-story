package com.nttstory.story.service.impl;

import com.nttstory.story.dto.LoginResponseForm;
import com.nttstory.story.dto.LoginRequestForm;
import com.nttstory.story.dto.UserRequestForm;
import com.nttstory.story.exception.ItemNotFoundException;
import com.nttstory.story.model.Role;
import com.nttstory.story.model.User;
import com.nttstory.story.projection.UsersProjection;
import com.nttstory.story.repository.UserRepository;
import com.nttstory.story.service.JwtService;
import com.nttstory.story.service.MediaService;
import com.nttstory.story.service.RoleService;
import com.nttstory.story.service.UserService;
import com.nttstory.story.util.JwtConfig;
import com.nttstory.story.util.UserDetailsCustom;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.*;


@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleService roleService;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private MediaService mediaService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private JwtConfig jwtConfig;

    @Override
    public LoginResponseForm authenticate(LoginRequestForm requestForm) {

        //start login
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        requestForm.getEmail(),
                        requestForm.getPassword()
                )
        );
        //end login
        UserDetailsCustom userDetailsCustom = (UserDetailsCustom) authentication.getPrincipal();
        Date expiration = new Date(System.currentTimeMillis() + jwtConfig.getJwtExpiration());
        Date issuedAt = new Date(System.currentTimeMillis());
        String id = UUID.randomUUID().toString();
        String accessToken = jwtService.generateJwt(userDetailsCustom, expiration, issuedAt, id);
        LoginResponseForm response = new LoginResponseForm(
                accessToken,
                expiration.getTime(),
                issuedAt.getTime(),
                id
        );
        return response;
    }

    @Override
    public List<UsersProjection> getAllUsers() {
        return userRepository.findAllUsers();
    }

    @Override
    public Optional<User> getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public Optional<User> getUserById(Long id) {
        return userRepository.findById(id);
    }

    @Override
    public User addUser(UserRequestForm requestForm) {
        User user = new User();
        user.setEmail(requestForm.getEmail());
        user.setFirstName(requestForm.getFirstName());
        user.setPassword(passwordEncoder.encode(requestForm.getPassword()));
        user.setLastName(requestForm.getLastName());

        Set<Role> roles = Set.of(roleService.getRoleByName("USER").orElseThrow(
                () -> new ItemNotFoundException("No found ROLE with name is USER")
        ));
        user.setRoles(roles);
        if (!ObjectUtils.isEmpty(requestForm.getAvatarForm())) {
            String fileName = mediaService.save(requestForm.getAvatarForm());
        }
        return userRepository.save(user);
    }
}
