package ru.kata.spring.boot_security.demo.services;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.kata.spring.boot_security.demo.exeptions.UserExistsException;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.repositories.UserRepository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService{

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleService roleService;

    public UserServiceImpl(UserRepository userRepository,
                           PasswordEncoder passwordEncoder,
                           RoleService roleService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.roleService = roleService;
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public User getUser(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    @Override
    @Transactional
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    @Override
    @Transactional
    public User createUser(User user) {
//        User existUser = userRepository.findByUserName(user.getUsername());
//        if (existUser != null) {
//            UserExistsException exception = new UserExistsException(user);
//            exception.getMessage();
//            throw exception;
//        }
//
//        user.setPassword(passwordEncoder.encode(user.getPassword()));
//        Set<String> roles = user.getRoles().stream()
//                .map(Role::getRoleName)
//                .collect(Collectors.toSet());
//
//        Set<Role> existRoles = roleService.getRoleSet(roles);
//
//        user.setRoles(existRoles);
//        existRoles.forEach(role -> role.setUserSet(Set.of(user)));
        return userRepository.save(user);
    }

    @Override
    @Transactional
    public void editUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
    }
    @Override
    @Transactional
    public void saveUser(User user) {
//        User existUser = userRepository.findByUserName(user.getUsername());
//        if (existUser != null) {
//            UserExistsException exception = new UserExistsException(user);
//            exception.getMessage();
//            throw exception;
//        }
        System.out.println(user.getRoles());
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
    }
}
