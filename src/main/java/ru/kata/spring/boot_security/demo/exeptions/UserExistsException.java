package ru.kata.spring.boot_security.demo.exeptions;

import ru.kata.spring.boot_security.demo.model.User;

import javax.persistence.EntityExistsException;

public class UserExistsException extends EntityExistsException {

    private User user;
    public UserExistsException(User user) {
        this.user = user;
    }

    @Override
    public String getMessage() {
        StringBuilder builder = new StringBuilder();
        builder.append("User with login ")
                .append(user.getUsername())
                .append(" already exists.");
        return super.getMessage();
    }
}
