package panda.repository;

import java.util.List;

public interface GenericRepository<Entity, Id> {

    void save(Entity entity);

    Entity findById(Id id);

    List<Entity> findAll();

}
