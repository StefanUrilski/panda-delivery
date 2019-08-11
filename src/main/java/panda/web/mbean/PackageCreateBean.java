package panda.web.mbean;

import org.modelmapper.ModelMapper;
import panda.domain.model.binding.PackageCreateBindingModel;
import panda.domain.model.service.PackageServiceModel;
import panda.domain.model.service.UserServiceModel;
import panda.service.PackageService;
import panda.service.UserService;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Named(value = "packageCreate")
@RequestScoped
public class PackageCreateBean {

    private List<String> users;
    private PackageCreateBindingModel model;

    private PackageService packageService;
    private UserService userService;
    private ModelMapper modelMapper;

    public PackageCreateBean() {
    }

    @Inject
    public PackageCreateBean(PackageService packageService, UserService userService, ModelMapper modelMapper) {
        this.packageService = packageService;
        this.userService = userService;
        this.modelMapper = modelMapper;
    }

    @PostConstruct
    private void init() {
        model = new PackageCreateBindingModel();

        users = userService.findAllUsers()
                .stream()
                .map(UserServiceModel::getUsername)
                .collect(Collectors.toList());
    }

    public List<String> getUsers() {
        return users;
    }

    public void setUsers(List<String> users) {
        this.users = users;
    }

    public PackageCreateBindingModel getModel() {
        return model;
    }

    public void setModel(PackageCreateBindingModel model) {
        this.model = model;
    }

    public void create() throws IOException {
        PackageServiceModel packageServiceModel = modelMapper
                .map(model, PackageServiceModel.class);

        packageServiceModel
                .setRecipient(userService.findUserByUsername(model.getRecipient()));

        packageService
                .packageCreate(packageServiceModel);

        FacesContext.getCurrentInstance()
                .getExternalContext()
                .redirect("/faces/view/home.xhtml");
    }
}
