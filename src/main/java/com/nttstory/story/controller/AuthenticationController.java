package com.nttstory.story.controller;


import com.nttstory.story.dto.BaseResponse;
import com.nttstory.story.dto.LoginRequestForm;
import com.nttstory.story.dto.RoleRequestForm;
import com.nttstory.story.dto.UserRequestForm;
import com.nttstory.story.model.Role;
import com.nttstory.story.model.User;
import com.nttstory.story.service.RoleService;
import com.nttstory.story.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.NoHandlerFoundException;

import java.util.Optional;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {

    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;



    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequestForm requestForm) {
        BaseResponse response = new BaseResponse(HttpStatus.OK.value(), null, userService.authenticate(requestForm));
        return ResponseEntity.ok(response);
    }

    @PostMapping("/register")
    public ResponseEntity<?> register (@ModelAttribute UserRequestForm requestForm) {
        BaseResponse response = new BaseResponse(HttpStatus.OK.value(), null, userService.addUser(requestForm));
        return ResponseEntity.ok(response);
    }
    @PostMapping("/add-role")
    public ResponseEntity<?> addRole(@Valid @RequestBody RoleRequestForm requestForm) {
        BaseResponse response = new BaseResponse(HttpStatus.OK.value(), null, roleService.addRole(requestForm));
        return ResponseEntity.ok(response);
    }
    @GetMapping("/get-role")
    public ResponseEntity<?> getRole(@RequestParam(required = false) Long id, @RequestParam(required = false) String name) throws NoHandlerFoundException {
       Optional<Role> role = null;
        if(ObjectUtils.isEmpty(id) && ObjectUtils.isEmpty(name)) {
            String requestUri = "/get-role";
            throw new NoHandlerFoundException("POST", requestUri, new HttpHeaders());
        }
        if(!ObjectUtils.isEmpty(id) && !ObjectUtils.isEmpty(name)) {
            String requestUri = "/get-role?id=" + id + "name=" + name;
            throw new NoHandlerFoundException("POST", requestUri, new HttpHeaders());
        }
        if(!ObjectUtils.isEmpty(id)) {
            role = roleService.getRoleById(id);
        } else if(!ObjectUtils.isEmpty(name)) {
            role = roleService.getRoleByName(name);
        }
        BaseResponse response = new BaseResponse(HttpStatus.OK.value(), null, role);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/update-role/{id}")
    public ResponseEntity<?> updateRole(@PathVariable Long id, @RequestBody RoleRequestForm requestForm) {
        BaseResponse response = new BaseResponse(HttpStatus.OK.value(), null, roleService.updateRole(id, requestForm));
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/get-roles")
    public ResponseEntity<?> getAllRoles() {
        BaseResponse response = new BaseResponse(HttpStatus.OK.value(), null, roleService.getAllRoles());
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @DeleteMapping("/del-role/{id}")
    public ResponseEntity<?> deleteRole(@PathVariable Long id) {
        roleService.deleteRole(id);
        BaseResponse response = new BaseResponse(HttpStatus.OK.value(), "delete success", null);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }


}
