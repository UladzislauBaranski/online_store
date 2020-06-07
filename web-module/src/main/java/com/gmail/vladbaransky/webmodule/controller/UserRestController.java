package com.gmail.vladbaransky.webmodule.controller;

import com.gmail.vladbaransky.servicemodule.UserService;
import com.gmail.vladbaransky.servicemodule.model.UserDTO;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class UserRestController {

    private final UserService userService;

    public UserRestController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/users")
    public UserDTO addUser(@RequestBody UserDTO user) {
        String hashedPassword = userService.getBCryptPassword();
        user.setPassword(hashedPassword);
        return userService.addUser(user);
    }
}

