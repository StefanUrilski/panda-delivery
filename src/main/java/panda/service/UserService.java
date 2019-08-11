package panda.service;

import panda.domain.model.service.UserServiceModel;

import java.util.List;

public interface UserService {

    boolean userRegister(UserServiceModel userServiceModel);

    UserServiceModel userLogin(UserServiceModel userServiceModel);

    UserServiceModel findUserByUsername(String username);

    List<UserServiceModel> findAllUsers();
}
