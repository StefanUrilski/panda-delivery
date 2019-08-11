package panda.repository;

import panda.domain.entity.Package;
import panda.domain.entity.enums.Status;

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
    public Package save(Package aPackage) {
        entityManager.getTransaction().begin();
        entityManager.persist(aPackage);
        entityManager.getTransaction().commit();

        return aPackage;
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

    @Override
    public Long size() {
        return this.entityManager
                .createQuery("select count(u) from User u ", Long.class)
                .getSingleResult();
    }

    @Override
    public List<Package> findAllPackagesByStatus(Status status) {
        this.entityManager.getTransaction().begin();
        List<Package> packages = this.entityManager
                .createQuery("select p from Package p where p.status = :status", Package.class)
                .setParameter("status", status)
                .getResultList();
        this.entityManager.getTransaction().commit();

        return packages;
    }

    @Override
    public Package updatePackage(Package aPackage) {
        this.entityManager.getTransaction().begin();
        Package updated = this.entityManager.merge(aPackage);
        this.entityManager.getTransaction().commit();

        return updated;
    }
}
