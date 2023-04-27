package com.nttstory.story.controller;


import com.nttstory.story.dto.BaseResponse;
import com.nttstory.story.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;
    @GetMapping
    public ResponseEntity<?> getAllUser() {
        BaseResponse response = new BaseResponse(HttpStatus.OK.value(), null, userService.getAllUsers());
        return ResponseEntity.ok(response);
    }
//    @PostMapping
//    public ResponseEntity<?> addUser() {
//        return ResponseEntity.ok("admin access");
//    }
//    @GetMapping("/{id}")
//    public ResponseEntity<?> getUser() {
//        return ResponseEntity.ok("guest access");
//    }
//    @GetMapping("/author")
//    public ResponseEntity<?> author() {
//        return ResponseEntity.ok("author access");
//    }

}
