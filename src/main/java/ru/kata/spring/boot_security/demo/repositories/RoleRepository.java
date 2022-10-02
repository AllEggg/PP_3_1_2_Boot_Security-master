package ru.kata.spring.boot_security.demo.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.kata.spring.boot_security.demo.model.Role;

import java.util.Set;

public interface RoleRepository extends JpaRepository<Role, Long> {

//    Set<Role> findByRoleIn(Set<String> roles);

    Role findRoleByRoleName(String name);
}
