package com.festnode.festnode.controller;

import com.festnode.festnode.dto.AuthResponseDto;
import com.festnode.festnode.dto.UserLoginDto;
import com.festnode.festnode.model.AppUser;
import com.festnode.festnode.service.AuthService;
import com.festnode.festnode.service.CustomUserDetailsServiceImpl;
import com.festnode.festnode.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {

    @Autowired
    private AuthService authService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private CustomUserDetailsServiceImpl customUserDetailsService;

    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody AppUser userData){
        Object user = authService.registerNewUser(userData);
        if (user instanceof String) {  // If the result is a message indicating failure
            return new ResponseEntity<>(user, HttpStatus.CONFLICT);  // Return 409 Conflict
        } else {
            return new ResponseEntity<>(user, HttpStatus.CREATED);  // Return 201 Created
        }
    }

    @PostMapping("/public/createUser")
    public ResponseEntity<?> createUser(@RequestBody AppUser userData) throws Exception {
        Object user = authService.createNewUser(userData);
        if (user instanceof String) {  // If the result is a message indicating failure
            return new ResponseEntity<>(user, HttpStatus.CONFLICT);  // Return 409 Conflict
        } else {
            return new ResponseEntity<>(user, HttpStatus.CREATED);  // Return 201 Created
        }
    }

    @PostMapping("/userlogin")
    public ResponseEntity<AuthResponseDto> loginUser(@RequestBody UserLoginDto credentials) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(credentials.getEmail(), credentials.getPassword()));
            UserDetails userDetails = customUserDetailsService.loadUserByUsername(credentials.getEmail());
            String jwtToken = jwtUtil.generateToken(userDetails.getUsername());
            AppUser user = authService.findByEmail(userDetails.getUsername());
            AuthResponseDto authResponse = new AuthResponseDto(user, jwtToken);
            return new ResponseEntity<>(authResponse, HttpStatus.OK);

        }
        catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/secure/fetchUser")
    public ResponseEntity<Object> fetchUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        AppUser user = authService.findByEmail(email);
        if (user == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(user, HttpStatus.OK);
        }
    }
}
