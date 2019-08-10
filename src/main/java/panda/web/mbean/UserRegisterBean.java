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
    private ReceiptService receiptService;
    private PackageService packageService;
    private ValidationUtil validationUtil;

    public UserRegisterBean() {
        this.userRegister = new UserRegisterBindingModel();
        this.validationUtil = new ValidationUtilImpl();
    }

    @Inject
    public UserRegisterBean(ModelMapper modelMapper,
                            UserService userService,
                            ReceiptService receiptService,
                            PackageService packageService) {
        this();
        this.modelMapper = modelMapper;
        this.userService = userService;
        this.receiptService = receiptService;
        this.packageService = packageService;

    }

    public UserRegisterBindingModel getUserRegister() {
        return userRegister;
    }

    public void setUserRegister(UserRegisterBindingModel userRegister) {
        this.userRegister = userRegister;
    }

    public void submitUser() throws IOException {
        if (!userRegister.getPassword().equals(userRegister.getConfirmPassword())) {
            return;
        }

        if (!validationUtil.isValid(userRegister)) {
            return;
        }

        UserServiceModel user = modelMapper.map(userRegister, UserServiceModel.class);

        if (!userService.saveUser(user)) {
            return;
        }

        FacesContext.getCurrentInstance().getExternalContext().redirect("/");
    }
}
