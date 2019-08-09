package panda.repository;

import panda.domain.entity.Receipt;
import panda.domain.entity.User;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import java.util.List;

public class ReceiptRepositoryImpl implements ReceiptRepository {

    private final EntityManager entityManager;

    @Inject
    public ReceiptRepositoryImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public void save(Receipt receipt) {
        entityManager.getTransaction().begin();
        entityManager.persist(receipt);
        entityManager.getTransaction().commit();
    }

    @Override
    public Receipt findById(String id) {
        return entityManager
                .createQuery("select r from Receipt as r where r.id = :id",
                        Receipt.class)
                .setParameter("id", id)
                .getSingleResult();
    }

    @Override
    public List<Receipt> findAll() {
        return entityManager
                .createQuery("select r from Receipt as r", Receipt.class)
                .getResultList();
    }
}
