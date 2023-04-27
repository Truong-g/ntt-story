package com.nttstory.story.service;

import com.nttstory.story.dto.LoginResponseForm;
import com.nttstory.story.dto.LoginRequestForm;
import com.nttstory.story.dto.UserRequestForm;
import com.nttstory.story.model.User;
import com.nttstory.story.projection.UsersProjection;

import java.util.List;
import java.util.Optional;

public interface UserService {

    LoginResponseForm authenticate(LoginRequestForm requestForm);


    List<UsersProjection> getAllUsers();

    Optional<User> getUserByEmail(String email);

    Optional<User> getUserById(Long id);


    User addUser(UserRequestForm user);




}
