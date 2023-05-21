package ru.kata.spring.boot_security.demo.service;


import org.springframework.security.core.userdetails.UserDetailsService;
import ru.kata.spring.boot_security.demo.model.User;

import java.util.List;

public interface UserService extends UserDetailsService {
    void add(User user);

    List<User> listUsers();

    User showUser(long id);

    void update(long id, User user);

    void delete(long id);
}
