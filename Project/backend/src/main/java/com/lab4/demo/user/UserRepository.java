package com.lab4.demo.user;

import com.lab4.demo.user.model.Role;
import com.lab4.demo.user.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {



    List<User> findUserByRoles(Role Roles);

    List<User> findAllByRoles(Role role);

    Optional<User> findByUsername(String username);

    Boolean existsByUsername(String username);

    Boolean existsByEmail(String email);
}
