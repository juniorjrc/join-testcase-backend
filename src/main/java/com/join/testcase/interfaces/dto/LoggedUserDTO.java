package com.join.testcase.interfaces.dto;

public record LoggedUserDTO(
        String username,
        String login,
        String token
) {}
