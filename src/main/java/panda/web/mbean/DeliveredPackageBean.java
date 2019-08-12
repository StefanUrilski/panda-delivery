package panda.web.mbean;

import org.modelmapper.ModelMapper;
import panda.domain.entity.enums.Status;
import panda.domain.model.view.PackageViewModel;
import panda.service.PackageService;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.util.List;
import java.util.stream.Collectors;

@Named(value = "deliveredBean")
@RequestScoped
public class DeliveredPackageBean {

    private List<PackageViewModel> packages;

    private PackageService packageService;
    private ModelMapper modelMapper;

    public DeliveredPackageBean() {
    }

    @Inject
    public DeliveredPackageBean(PackageService packageService,
                                ModelMapper modelMapper) {
        this.packageService = packageService;
        this.modelMapper = modelMapper;
    }

    @PostConstruct
    private void initPackages() {
        this.packages = this.packageService
                .findAllPackagesByStatus(Status.Delivered)
                .stream()
                .map(p -> {
                    PackageViewModel aPackage = this.modelMapper.map(p, PackageViewModel.class);
                    aPackage.setRecipient(p.getRecipient().getUsername());

                    return aPackage;
                }).collect(Collectors.toList());
    }

    public List<PackageViewModel> getPackages() {
        return packages;
    }

    public void setPackages(List<PackageViewModel> packages) {
        this.packages = packages;
    }

}
