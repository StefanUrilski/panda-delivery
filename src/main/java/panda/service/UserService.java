package panda.service;

import panda.domain.model.service.UserServiceModel;

public interface UserService {

    boolean saveUser(UserServiceModel userServiceModel);

    UserServiceModel userExist(String username, String password);
}
