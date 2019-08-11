package panda.web.mbean;

import org.modelmapper.ModelMapper;
import panda.domain.model.binding.UserLoginBindingModel;
import panda.domain.model.service.UserServiceModel;
import panda.service.UserService;

import javax.enterprise.context.RequestScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@Named(value = "login")
@RequestScoped
public class UserLoginBean {

    private UserLoginBindingModel user;

    private ModelMapper modelMapper;
    private UserService userService;

    public UserLoginBean() {
    }

    @Inject
    public UserLoginBean(UserService userService,
                         ModelMapper modelMapper) {
        this.userService = userService;
        this.modelMapper = modelMapper;
        this.user = new UserLoginBindingModel();
    }

    public UserLoginBindingModel getUser() {
        return user;
    }

    public void setUser(UserLoginBindingModel userLoginBindingModel) {
        this.user = userLoginBindingModel;
    }

    public void login() throws IOException {
        UserServiceModel userServiceModel = userService.userLogin(modelMapper.map(user, UserServiceModel.class));

        ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();

        if (userServiceModel == null) {
            context.redirect("/faces/view/login.xhtml");
            return;
        }

        HttpSession session = (HttpSession) context.getSession(false);

        session.setAttribute("username", userServiceModel.getUsername());
        session.setAttribute("role", userServiceModel.getRole());

        context.redirect("/faces/view/home.xhtml");
    }
}
