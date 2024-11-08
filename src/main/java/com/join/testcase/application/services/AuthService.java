package com.join.testcase.application.services;

import com.join.testcase.domain.entities.User;
import com.join.testcase.infrastructure.config.jwt.JwtTokenProvider;
import com.join.testcase.infrastructure.exceptions.UnauthorizedException;
import com.join.testcase.interfaces.dto.CredentialsDTO;
import com.join.testcase.interfaces.dto.LoggedUserDTO;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AuthService {

    private static final String USERNAME_OR_PASSWORD_INVALID = "Username or password invalid!";

    private final AuthenticationManager authenticationManager;
    private final UserService userService;
    private final JwtTokenProvider tokenProvider;

    public LoggedUserDTO auth(final CredentialsDTO credentialsDTO) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                    credentialsDTO.login(),
                    credentialsDTO.password()
            ));
            UserDetails userDetails = this.userService.loadUserByUsername(credentialsDTO.login());
            String token = this.tokenProvider.createToken(credentialsDTO.login(), userDetails.getAuthorities());
            final User user = this.userService.getByLogin(credentialsDTO.login());
            return new LoggedUserDTO(user.getUserName(), user.getLogin(), token);
        } catch (AuthenticationException e) {
            throw new UnauthorizedException(USERNAME_OR_PASSWORD_INVALID);
        }
    }
}
