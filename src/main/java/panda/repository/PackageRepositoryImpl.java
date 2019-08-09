package panda.repository;

import panda.domain.entity.Package;
import panda.domain.entity.User;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import java.util.List;

public class PackageRepositoryImpl implements PackageRepository {

    private final EntityManager entityManager;

    @Inject
    public PackageRepositoryImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public void save(Package aPackage) {
        entityManager.getTransaction().begin();
        entityManager.persist(aPackage);
        entityManager.getTransaction().commit();
    }

    @Override
    public Package findById(String id) {
        return entityManager
                .createQuery("select p from Package as p where p.id = :id",
                        Package.class)
                .setParameter("id", id)
                .getSingleResult();
    }

    @Override
    public List<Package> findAll() {
        return entityManager
                .createQuery("select p from Package as p", Package.class)
                .getResultList();
    }
}
