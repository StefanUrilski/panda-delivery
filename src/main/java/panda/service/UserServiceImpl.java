package panda.service;

import org.apache.commons.codec.digest.DigestUtils;
import org.modelmapper.ModelMapper;
import panda.domain.entity.User;
import panda.domain.entity.enums.Role;
import panda.domain.model.service.UserServiceModel;
import panda.repository.UserRepository;

import javax.inject.Inject;
import java.util.List;
import java.util.stream.Collectors;

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
    public boolean userRegister(UserServiceModel userServiceModel) {
        userServiceModel.setPassword(DigestUtils.sha256Hex(userServiceModel.getPassword()));
        User user = modelMapper.map(userServiceModel, User.class);

        user.setRole(userRepository.size() == 0 ? Role.Admin : Role.User);

        try {
            userRepository.save(user);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public UserServiceModel userLogin(UserServiceModel userService) {
        User user;

        try {
            user = userRepository.findByUsername(userService.getUsername());
        } catch (Exception e) {
            return null;
        }

        if (user == null || !DigestUtils.sha256Hex(userService.getPassword()).equals(user.getPassword())) {
            return null;
        }

        return modelMapper.map(user, UserServiceModel.class);
    }

    @Override
    public UserServiceModel findUserByUsername(String username) {
        return modelMapper.map(userRepository.findByUsername(username), UserServiceModel.class);
    }

    @Override
    public List<UserServiceModel> findAllUsers() {
        return this.userRepository.findAll()
                .stream()
                .map(u -> this.modelMapper.map(u, UserServiceModel.class))
                .collect(Collectors.toList());
    }
}
