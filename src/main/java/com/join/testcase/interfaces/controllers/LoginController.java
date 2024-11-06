package com.join.testcase.interfaces.controllers;

import com.join.testcase.application.services.AuthService;
import com.join.testcase.interfaces.dto.LoggedUserDTO;
import com.join.testcase.interfaces.dto.CredentialsDTO;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/login")
@AllArgsConstructor
public class LoginController {

    private final AuthService authService;

    @PostMapping
    public ResponseEntity<LoggedUserDTO> auth(
            @RequestBody CredentialsDTO credentialsDTO) {
        return ResponseEntity.ok(authService.auth(credentialsDTO));
    }
}
