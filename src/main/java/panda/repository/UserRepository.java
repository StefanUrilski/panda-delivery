package panda.repository;

import panda.domain.entity.User;

public interface UserRepository extends GenericRepository<User, String> {

    User exists(String username, String password);

    User findByUsername(String username);
}
