package panda.repository;

import panda.domain.entity.User;

public interface UserRepository extends GenericRepository<User, String> {

    long count();

    User exists(String username, String password);

}
