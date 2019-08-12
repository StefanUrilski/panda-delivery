package panda.web.mbean;

import org.modelmapper.ModelMapper;
import panda.domain.entity.enums.Status;
import panda.domain.model.view.PackageViewModel;
import panda.service.PackageService;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Named(value = "shippedBean")
@RequestScoped
public class ShippedPackagesBean {

    private List<PackageViewModel> packages;

    private PackageService packageService;
    private ModelMapper modelMapper;

    public ShippedPackagesBean() {
    }

    @Inject
    public ShippedPackagesBean(PackageService packageService,
                               ModelMapper modelMapper) {
        this.packageService = packageService;
        this.modelMapper = modelMapper;
    }

    @PostConstruct
    private void initPackages() {
        this.packages = this.packageService
                .findAllPackagesByStatus(Status.Shipped)
                .stream()
                .map(p -> {
                    PackageViewModel aPackage = this.modelMapper.map(p, PackageViewModel.class);
                    aPackage.setRecipient(p.getRecipient().getUsername());

                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
                    aPackage.setEstimatedDeliveryTime(p.getEstimatedDeliveryTime().format(formatter));

                    return aPackage;
                }).collect(Collectors.toList());
    }

    public List<PackageViewModel> getPackages() {
        return packages;
    }

    public void setPackages(List<PackageViewModel> packages) {
        this.packages = packages;
    }

    public void changeStatus(String id) throws IOException {
        this.packageService.packageStatusChange(id, Status.Delivered);

        FacesContext.getCurrentInstance()
                .getExternalContext()
                .redirect("/faces/view/admin/shipped-package.xhtml");
    }
}
