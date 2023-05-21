package ru.kata.spring.boot_security.demo.dao;


import org.springframework.security.core.userdetails.UserDetails;
import ru.kata.spring.boot_security.demo.model.User;

import java.util.List;

public interface UserDao {

    void add(User user);

    List<User> listUsers();

    User showUser(long id);

    void update(long id, User user);

    void delete(long id);

    UserDetails findByUsername(String username);
}
