package panda.web.mbean;

import org.modelmapper.ModelMapper;
import panda.domain.entity.enums.Status;
import panda.domain.model.service.PackageServiceModel;
import panda.domain.model.service.ReceiptServiceModel;
import panda.domain.model.view.PackageViewModel;
import panda.service.PackageService;
import panda.service.ReceiptService;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Named(value = "shippedBean")
@RequestScoped
public class ShippedPackagesBean {

    private List<PackageViewModel> packages;

    private ModelMapper modelMapper;
    private PackageService packageService;
    private ReceiptService receiptService;

    public ShippedPackagesBean() {
    }

    @Inject
    public ShippedPackagesBean(ModelMapper modelMapper,
                               PackageService packageService,
                               ReceiptService receiptService) {
        this.modelMapper = modelMapper;
        this.packageService = packageService;
        this.receiptService = receiptService;
    }

    @PostConstruct
    private void initPackages() {
        packages = packageService
                .findAllPackagesByStatus(Status.Shipped)
                .stream()
                .map(p -> {
                    PackageViewModel aPackage = modelMapper.map(p, PackageViewModel.class);
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
        // Changing status on package
        packageService.packageStatusChange(id, Status.Delivered);

        // Generating a receipt
        ReceiptServiceModel receipt = new ReceiptServiceModel();
        PackageViewModel packageViewModel = packages.stream().filter(p -> p.getId().equals(id)).findFirst().get();

        receipt.setFee(BigDecimal.valueOf(packageViewModel.getWeight() * 2.67));
        receipt.setIssuedOn(LocalDateTime.now());
        receipt.setaPackage(modelMapper.map(packageViewModel, PackageServiceModel.class));
        receipt.setRecipient(packageViewModel.getRecipient());

        receiptService.generateReceipt(receipt);

        // redirect
        FacesContext.getCurrentInstance()
                .getExternalContext()
                .redirect("/faces/view/admin/shipped-package.xhtml");
    }
}
