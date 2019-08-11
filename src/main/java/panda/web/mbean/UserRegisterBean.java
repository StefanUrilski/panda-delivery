package panda.web.mbean;

import org.modelmapper.ModelMapper;
import panda.domain.model.binding.UserRegisterBindingModel;
import panda.domain.model.service.UserServiceModel;
import panda.repository.PackageRepository;
import panda.service.PackageService;
import panda.service.ReceiptService;
import panda.service.UserService;
import panda.util.ValidationUtil;
import panda.util.ValidationUtilImpl;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.IOException;

@Named(value = "register")
@RequestScoped
public class UserRegisterBean {

    private UserRegisterBindingModel userRegister;

    private ModelMapper modelMapper;
    private UserService userService;
    private ValidationUtil validationUtil;

    public UserRegisterBean() {
    }

    @Inject
    public UserRegisterBean(ModelMapper modelMapper,
                            UserService userService) {
        this.modelMapper = modelMapper;
        this.userService = userService;
    }

    @PostConstruct
    private void init() {
        this.userRegister = new UserRegisterBindingModel();
        this.validationUtil = new ValidationUtilImpl();
    }

    public UserRegisterBindingModel getUserRegister() {
        return userRegister;
    }

    public void setUserRegister(UserRegisterBindingModel userRegister) {
        this.userRegister = userRegister;
    }

    public void submitUser() throws IOException {
        if (!userRegister.getPassword().equals(userRegister.getConfirmPassword())) { return; }

        if (!validationUtil.isValid(userRegister)) { return; }

        UserServiceModel user = modelMapper.map(userRegister, UserServiceModel.class);

        if (! userService.userRegister(user)) { return; }

        FacesContext.getCurrentInstance().getExternalContext().redirect("/faces/view/login.xhtml");
    }
}
