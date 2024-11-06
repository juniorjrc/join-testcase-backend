package com.join.testcase.application.services;

import com.join.testcase.domain.entities.User;
import com.join.testcase.domain.repositories.UserRepository;
import com.join.testcase.infrastructure.exceptions.NotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import static java.util.Objects.isNull;
import static org.springframework.security.core.authority.AuthorityUtils.createAuthorityList;

@Service
@AllArgsConstructor
public class UserService implements UserDetailsService {

    private static final String USER_NOT_FOUND = "User not found!";

    private final UserRepository userRepository;

    public User getByLogin(final String login) {
        return this.userRepository.findByLogin(login);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = this.userRepository.findByLogin(username);

        if(isNull(user)) {
            throw new NotFoundException(USER_NOT_FOUND);
        }

        return new org.springframework.security.core.userdetails.User(
                user.getLogin(),
                user.getPassword(),
                createAuthorityList()
        );
    }
}
