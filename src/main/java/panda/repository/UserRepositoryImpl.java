package panda.repository;

import panda.domain.entity.User;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import java.util.List;

public class UserRepositoryImpl implements UserRepository {

    private final EntityManager entityManager;

    @Inject
    public UserRepositoryImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public void save(User user) {
        entityManager.getTransaction().begin();
        entityManager.persist(user);
        entityManager.getTransaction().commit();
    }

    @Override
    public User findById(String id) {
        return entityManager
                .createQuery("select u from User as u where u.id = :id", User.class)
                .setParameter("id", id)
                .getSingleResult();
    }

    @Override
    public List<User> findAll() {
        return entityManager
                .createQuery("select u from User as u", User.class)
                .getResultList();
    }
}
