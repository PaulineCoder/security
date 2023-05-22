package ru.kata.spring.boot_security.demo.dao;


import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import ru.kata.spring.boot_security.demo.model.User;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.List;


@Component
public class UserDaoImp implements UserDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<User> listUsers() {
        TypedQuery<User> query = entityManager.createQuery("select u from User u ", User.class);
        return query.getResultList();
    }


    public User showUser(long id) {
        TypedQuery<User> query = entityManager.createQuery("select u from User u where u.id = :id", User.class);
        query.setParameter("id", id);
        return query.getResultList().stream().findAny().orElse(null);
    }

    @Override
    public void add(User user) {
        entityManager.persist(user);
        if(user.getRoleName().equals("ROLE_ADMIN")) {
            Query query = entityManager.createNativeQuery("insert into m_users_roles(user_id, roles_id) VALUES (?,?)");
            query.setParameter(1,user.getId());
            query.setParameter(2, 2);
            query.executeUpdate();
        }
        Query query = entityManager.createNativeQuery("insert into m_users_roles(user_id, roles_id) VALUES (?,?)");
        query.setParameter(1,user.getId());
        query.setParameter(2, 1);
        query.executeUpdate();
    }


    @Override
    public void update(long id, User user) {
        entityManager.merge(user);
    }

    @Override
    public void delete(long id) {
        entityManager.remove(entityManager.find(User.class, id));
    }

    @Override
    public UserDetails findByUsername(String username) {
        TypedQuery<User> query = entityManager.createQuery("select u from User u join fetch u.roles where u.name = :name", User.class);
        query.setParameter("name", username);
        return query.getResultList().stream().findAny().orElse(null);
    }
}
