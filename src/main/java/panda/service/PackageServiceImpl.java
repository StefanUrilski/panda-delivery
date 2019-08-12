package panda.service;

import org.modelmapper.ModelMapper;
import panda.domain.entity.Package;
import panda.domain.entity.enums.Status;
import panda.domain.model.service.PackageServiceModel;
import panda.repository.PackageRepository;

import javax.inject.Inject;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class PackageServiceImpl implements PackageService {

    private final ModelMapper modelMapper;
    private final PackageRepository packageRepository;

    @Inject
    public PackageServiceImpl(ModelMapper modelMapper,
                              PackageRepository packageRepository) {
        this.modelMapper = modelMapper;
        this.packageRepository = packageRepository;
    }

    @Override
    public void packageCreate(PackageServiceModel packageServiceModel) {
        Package aPackage = modelMapper.map(packageServiceModel, Package.class);
        aPackage.setStatus(Status.Pending);

        packageRepository.save(aPackage);
    }

    @Override
    public List<PackageServiceModel> findAllPackagesByStatus(Status status) {
        return packageRepository
                .findAllPackagesByStatus(status)
                .stream()
                .map(p -> modelMapper.map(p, PackageServiceModel.class))
                .collect(Collectors.toList());
    }

    @Override
    public void packageStatusChange(String id) {
        Package aPackage = packageRepository.findById(id);
        aPackage.setStatus(Status.Shipped);
        changeDeliveryDate(aPackage);

        packageRepository.updatePackage(aPackage);
    }

    private void changeDeliveryDate(Package aPackage) {
        long days = (System.currentTimeMillis() % 21) + 20;
        aPackage.setEstimatedDeliveryDate(LocalDateTime.now().plusDays(days));
    }

    @Override
    public PackageServiceModel findPackageById(String id) {
        Package aPackage;
        try {
            aPackage = packageRepository.findById(id);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

        return modelMapper.map(aPackage, PackageServiceModel.class);
    }
}
