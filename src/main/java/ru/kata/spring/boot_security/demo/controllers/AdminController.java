package ru.kata.spring.boot_security.demo.controllers;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.services.RoleService;
import ru.kata.spring.boot_security.demo.services.UserService;

import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final UserService userService;
    private final RoleService roleService;
    private final PasswordEncoder passwordEncoder;

    public AdminController(UserService userService, RoleService roleService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.roleService = roleService;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping
    public String getAllUsers(Model model) {
        model.addAttribute("users", userService.getAllUsers());
        return "userPage";
    }

    @PostMapping(value = "/delete-user{id}")
    public String deleteUser(@PathVariable("id") Long id) {
        userService.deleteUser(id);
        return "redirect:/admin";
    }

    @GetMapping("/user_details_form")
    public String newUser(Model model) {
        User user = new User();
        model.addAttribute("user", user);
        model.addAttribute("roles", roleService.getAllRoles().stream().map(Role::getRoleName).toArray());
        return "create_user";
    }
        @GetMapping("/user_form{id}")
        public String editUserForm(@PathVariable(value = "id", required = false) Long id, Model model) {
            model.addAttribute("roles", roleService.getAllRoles());
            if (id == null) {
                model.addAttribute("user", new User());
                return "create_user";
            }
            User user = userService.getUser(id);
            model.addAttribute("user", user);
            return "edit_user";
        }


    @PostMapping("/save")
    public String saveUser(@ModelAttribute("user") User user, @RequestParam Map<String, String> form) {
        Set<Role> roleSet = new HashSet<>();
        Set<Role> existRoleSet = roleService.getAllRoles();

        Collection<String> stringRoles = existRoleSet.stream().map(Role::getRoleName).collect(Collectors.toSet());

        for(String field : form.keySet()) {
            if (stringRoles.contains(field)) {
                Role role = roleService.getRoleByName(field);
                roleSet.add(role);
            }
        }

        user.setRoles(roleSet);
        userService.saveUser(user);
        return "redirect:/admin";
    }
}
