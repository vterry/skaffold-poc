package com.sample.admsys.controller;

import com.sample.admsys.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/admin")
public class AdminController {

    @Autowired
    private UserService userService;

    @GetMapping("all")
    public ResponseEntity<?> findAllUsers(){
        return ResponseEntity.ok(userService.findAllUsers());
    }
}
