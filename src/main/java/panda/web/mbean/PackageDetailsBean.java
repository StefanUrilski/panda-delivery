package panda.web.mbean;

import org.modelmapper.ModelMapper;
import panda.domain.entity.enums.Status;
import panda.domain.model.service.PackageServiceModel;
import panda.domain.model.view.PackageViewModel;
import panda.service.PackageService;

import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import java.time.format.DateTimeFormatter;
import java.util.Map;

@Named(value = "details")
public class PackageDetailsBean {

    private PackageViewModel aPackage;

    private ModelMapper modelMapper;
    private PackageService packageService;

    public PackageDetailsBean() {
    }

    @Inject
    public PackageDetailsBean(ModelMapper modelMapper,
                              PackageService packageService) {
        this.modelMapper = modelMapper;
        this.packageService = packageService;
    }

    @PostConstruct
    private void init() {
        Map<String, String> requestParameters = FacesContext
                .getCurrentInstance()
                .getExternalContext()
                .getRequestParameterMap();

        String packageId = requestParameters.get("id");

        PackageServiceModel p = packageService.findPackageById(packageId);

        this.aPackage = modelMapper.map(p, PackageViewModel.class);

        aPackage.setRecipient(p.getRecipient().getUsername());

        if (aPackage.getStatus().equals("Delivered") || aPackage.getStatus().equals("Acquired")) {
            aPackage.setEstimatedDeliveryTime("Delivered");
        }

        if (p.getStatus() == Status.Pending) {
            aPackage.setEstimatedDeliveryTime("N/A");
        } else if (p.getStatus() == Status.Shipped) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
            aPackage.setEstimatedDeliveryTime(p.getEstimatedDeliveryTime().format(formatter));
        }
    }

    public PackageViewModel getaPackage() {
        return aPackage;
    }

    public void setaPackage(PackageViewModel aPackage) {
        this.aPackage = aPackage;
    }
}
