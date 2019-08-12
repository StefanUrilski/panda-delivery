package panda.service;

import panda.domain.entity.enums.Status;
import panda.domain.model.service.PackageServiceModel;

import java.util.List;

public interface PackageService {

    void packageCreate(PackageServiceModel packageServiceModel);

    List<PackageServiceModel> findAllPackagesByStatus(Status status);

    void packageStatusChange(String id);

    PackageServiceModel findPackageById(String id);
}
