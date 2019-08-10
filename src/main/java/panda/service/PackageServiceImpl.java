package panda.service;

import org.modelmapper.ModelMapper;
import panda.repository.PackageRepository;

import javax.inject.Inject;

public class PackageServiceImpl implements PackageService {

    private final ModelMapper modelMapper;
    private final PackageRepository packageRepository;

    @Inject
    public PackageServiceImpl(ModelMapper modelMapper,
                              PackageRepository packageRepository) {
        this.modelMapper = modelMapper;
        this.packageRepository = packageRepository;
    }


}
