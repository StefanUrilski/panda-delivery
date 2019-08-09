package panda.service;

import org.modelmapper.ModelMapper;
import panda.domain.entity.User;
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
        try {
            userRepository.save(modelMapper.map(userServiceModel, User.class));
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
}
