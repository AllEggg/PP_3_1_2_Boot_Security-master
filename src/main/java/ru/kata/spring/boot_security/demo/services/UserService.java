package ru.kata.spring.boot_security.demo.services;


import ru.kata.spring.boot_security.demo.model.User;

import java.util.List;

public interface UserService {

    List<User> getAllUsers();
    User getUser(Long id);
    void deleteUser(Long id);
    void editUser(User user);
    void saveUser(User user);
    User findByUsername(String userName);
}
