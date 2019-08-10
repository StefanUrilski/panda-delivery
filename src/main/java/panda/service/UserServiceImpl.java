package panda.service;

import org.apache.commons.codec.digest.DigestUtils;
import org.modelmapper.ModelMapper;
import panda.domain.entity.User;
import panda.domain.entity.enums.Role;
import panda.domain.model.service.UserServiceModel;
import panda.repository.UserRepository;

import javax.inject.Inject;

public class UserServiceImpl implements UserService {

    private final ModelMapper modelMapper;
    private final UserRepository userRepository;

    @Inject
    public UserServiceImpl(ModelMapper modelMapper,
                           UserRepository userRepository) {
        this.modelMapper = modelMapper;
        this.userRepository = userRepository;
    }

    @Override
    public boolean saveUser(UserServiceModel userServiceModel) {
        userServiceModel.setPassword(DigestUtils.sha256Hex(userServiceModel.getPassword()));
        User user = modelMapper.map(userServiceModel, User.class);

        if (userRepository.count() == 0) {
            user.setRole(Role.Admin);
        } else {
            user.setRole(Role.User);
        }

        try {
            userRepository.save(user);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    public UserServiceModel userExist(String username, String password) {
        try {
            User user = userRepository.exists(username, DigestUtils.sha256Hex(password));
            return modelMapper.map(user, UserServiceModel.class);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
