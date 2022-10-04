package ru.kata.spring.boot_security.demo.controllers;

import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.kata.spring.boot_security.demo.exeptions.UserExistsException;
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
@Secured({"ROLE_ADMIN"})
@RequestMapping("/admin")
public class AdminController {

    private final UserService userService;
    private final RoleService roleService;

    public AdminController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
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
        if (userService.findByUsername(user.getUserName()) != null) {
            throw new UserExistsException(user);
        }
        Set<Role> roleSet = new HashSet<>();
        Collection<String> stringRoles = roleService.getAllRoles().stream()
                .map(Role::getRoleName)
                .collect(Collectors.toSet());

        for (String field : form.keySet()) {
            if (stringRoles.contains(field)) {
                roleSet.add(roleService.getRoleByName(field));
            }
        }
        user.setRoles(roleSet);
        if (user.getId() == null) {
            userService.saveUser(user);
        } else {
            userService.editUser(user);
        }
        return "redirect:/admin";
    }
}
