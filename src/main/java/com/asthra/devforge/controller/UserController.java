package com.asthra.devforge.controller;

import com.asthra.devforge.dto.RegisterRequest;
import com.asthra.devforge.dto.LoginRequest;
import com.asthra.devforge.entity.User;
import com.asthra.devforge.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.CrossOrigin;

@CrossOrigin(origins = {
        "http://localhost:5173",
        "https://asthra11-resumecraft-frontend.vercel.app"
})

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public User registerUser(@Valid @RequestBody RegisterRequest request){

        return userService.registerUser(request);

    }
    @PostMapping("/login")
    public User loginUser(@Valid @RequestBody LoginRequest request) {

        return userService.loginUser(request);

    }
}
