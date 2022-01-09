package com.sample.admsys.controller;

import com.sample.admsys.model.User;
import com.sample.admsys.service.AuthenticationService;
import com.sample.admsys.service.JwtRefreshTokenService;
import com.sample.admsys.service.UserService;
import com.sample.admsys.service.dto.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/authentication")
//@CrossOrigin
public class AuthenticationController {

    @Autowired
    private AuthenticationService authenticationService;

    @Autowired
    private UserService userService;

    @Autowired
    private JwtRefreshTokenService jwtRefreshTokenService;

    @PostMapping("signup")
    public ResponseEntity<?> signUp(@RequestBody UserDTO userDTO){
        if (userService.findByUsername(userDTO.getUsername()).isPresent()){
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
        return new ResponseEntity<>(userService.saveUser(userDTO), HttpStatus.CREATED);
    }

    @PostMapping("signin")
    public ResponseEntity<?> signIn(@RequestBody User user){
        return new ResponseEntity<>(authenticationService.signInAndReturnJwt(user), HttpStatus.OK);
    }

    @PostMapping("refreshtoken")
    public ResponseEntity<?> refreshToken(@RequestParam String token){
        return ResponseEntity.ok(jwtRefreshTokenService.generateAccessTokenFromRefreshToken(token));
    }
}
