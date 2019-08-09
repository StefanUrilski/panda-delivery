package panda.web.mbean;

import org.modelmapper.ModelMapper;
import panda.domain.model.binding.UserRegisterBindingModel;
import panda.domain.model.service.UserServiceModel;
import panda.service.UserService;
import panda.util.ValidationUtil;
import panda.util.ValidationUtilImpl;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.IOException;

@Named(value = "register")
public class UserRegisterBean {

    private UserRegisterBindingModel userRegister;

    private ModelMapper modelMapper;
    private UserService userService;
    private ValidationUtil validationUtil;

    public UserRegisterBean() {
        this.userRegister = new UserRegisterBindingModel();
        this.validationUtil = new ValidationUtilImpl();
    }

    @Inject
    public UserRegisterBean(ModelMapper modelMapper,
                            UserService userService) {
        this();
        this.modelMapper = modelMapper;
        this.userService = userService;
    }

    public UserRegisterBindingModel getUserRegister() {
        return userRegister;
    }

    public void setUserRegister(UserRegisterBindingModel userRegister) {
        this.userRegister = userRegister;
    }

    public void submitUser() throws IOException {
        if (! userRegister.getPassword().equals(userRegister.getConfirmPassword())) {
            return;
        }

        if (! validationUtil.isValid(userRegister)) {
            return;
        }

        UserServiceModel user = modelMapper.map(userRegister, UserServiceModel.class);

        if (! userService.saveUser(user)) {
            return;
        }

        FacesContext.getCurrentInstance().getExternalContext().redirect("/");
    }
}
