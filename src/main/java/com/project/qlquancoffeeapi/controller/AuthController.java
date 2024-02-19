package com.project.qlquancoffeeapi.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.project.qlquancoffeeapi.dto.LoginDto;
import com.project.qlquancoffeeapi.dto.SignUpDto;
import com.project.qlquancoffeeapi.entity.Role;
import com.project.qlquancoffeeapi.entity.User;
import com.project.qlquancoffeeapi.repository.RoleRepository;
import com.project.qlquancoffeeapi.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired private UserRepository userRepository;

    @Autowired private RoleRepository roleRepository;

    @Autowired private PasswordEncoder passwordEncoder;

    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@RequestBody LoginDto loginDto) {
        try {

            Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginDto.getUsername(), loginDto.getPassword()));
            SecurityContextHolder.getContext().setAuthentication(authentication);
            ObjectNode responseJson = new ObjectMapper().createObjectNode();
            responseJson.put("message", "User login successfully!");
            return new ResponseEntity<>(responseJson, HttpStatus.OK);
        } catch (AuthenticationException e) {
            // Xử lý lỗi xác thực và trả về đối tượng JSON lỗi
            ObjectNode errorJson = new ObjectMapper().createObjectNode();
            errorJson.put("error", "Authentication failed");
            errorJson.put("message", e.getMessage());

            return new ResponseEntity<>(errorJson, HttpStatus.UNAUTHORIZED);
        }
    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@RequestBody SignUpDto signUpDto)
    {
        if(userRepository.existsByUsername(signUpDto.getUsername()))
        {
            return new ResponseEntity<>("Username is already exist!", HttpStatus.BAD_REQUEST);
        }

        User user = new User();
        user.setUsername(signUpDto.getUsername());
        user.setPassword(passwordEncoder.encode(signUpDto.getPassword()));

        Role roles = roleRepository.findByName("ROLE_ADMIN").get();
        user.setRoles(Collections.singleton(roles));
        userRepository.save(user);
        return new ResponseEntity<>("User is registered successfully", HttpStatus.OK);
    }

}
