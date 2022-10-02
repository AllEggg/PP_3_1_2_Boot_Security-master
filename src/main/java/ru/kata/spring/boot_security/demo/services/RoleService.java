package ru.kata.spring.boot_security.demo.services;

import ru.kata.spring.boot_security.demo.model.Role;

import java.util.List;
import java.util.Set;

public interface RoleService {

//    Set<Role> getRoleSet(Set<String> rolesSet);

    Set<Role> getAllRoles();

    Role getRoleByName(String name);
}
