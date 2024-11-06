package com.join.testcase.domain.repositories;

import com.join.testcase.domain.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

    User findByLogin(final String login);
}
