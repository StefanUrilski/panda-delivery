package panda.web.mbean;

import org.modelmapper.ModelMapper;
import panda.domain.model.binding.UserLoginBindingModel;
import panda.domain.model.service.UserServiceModel;
import panda.domain.model.view.LoggedUserViewModel;
import panda.service.UserService;

import javax.enterprise.context.RequestScoped;
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
        user = new UserLoginBindingModel();
    }

    @Inject
    public UserLoginBean(ModelMapper modelMapper,
                         UserService userService) {
        this();
        this.modelMapper = modelMapper;
        this.userService = userService;
    }

    public UserLoginBindingModel getUser() {
        return user;
    }

    public void setUser(UserLoginBindingModel user) {
        this.user = user;
    }

    public void loginUser() throws IOException {
        UserServiceModel userServiceModel = userService.userExist(this.user.getUsername(), this.user.getPassword());
        FacesContext context = FacesContext.getCurrentInstance();
        if (userServiceModel == null) {
            context.getExternalContext().redirect("/faces/view/login.xhtml");
            return;
        }

        HttpSession session = (HttpSession) context.getExternalContext().getSession(true);
        session.setAttribute("user", modelMapper.map(userServiceModel, LoggedUserViewModel.class));

        context.getExternalContext().redirect("/faces/view/index.xhtml");
    }
}
