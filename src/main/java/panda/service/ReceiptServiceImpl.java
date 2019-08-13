package panda.service;

import org.modelmapper.ModelMapper;
import panda.domain.entity.Receipt;
import panda.domain.model.service.ReceiptServiceModel;
import panda.repository.PackageRepository;
import panda.repository.ReceiptRepository;
import panda.repository.UserRepository;

import javax.inject.Inject;
import java.util.List;
import java.util.stream.Collectors;

public class ReceiptServiceImpl implements ReceiptService {

    private final ModelMapper modelMapper;
    private final UserRepository userRepository;
    private final PackageRepository packageRepository;
    private final ReceiptRepository receiptRepository;

    @Inject
    public ReceiptServiceImpl(ModelMapper modelMapper,
                              UserRepository userRepository,
                              PackageRepository packageRepository,
                              ReceiptRepository receiptRepository) {
        this.modelMapper = modelMapper;
        this.userRepository = userRepository;
        this.packageRepository = packageRepository;
        this.receiptRepository = receiptRepository;
    }

    @Override
    public List<ReceiptServiceModel> findAllReceiptsByUsername(String username) {
        return receiptRepository.findAllByUsername(username)
                .stream()
                .map(receipt -> modelMapper.map(receipt, ReceiptServiceModel.class))
                .collect(Collectors.toList());
    }

    @Override
    public ReceiptServiceModel findReceiptById(String id) {
        Receipt receipt = receiptRepository.findById(id);
        ReceiptServiceModel receiptServiceModel = modelMapper.map(receipt, ReceiptServiceModel.class);

        receiptServiceModel.setRecipient(receipt.getRecipient().getUsername());
        return receiptServiceModel;
    }

    @Override
    public void generateReceipt(ReceiptServiceModel receiptServiceModel) {
        Receipt receipt = modelMapper.map(receiptServiceModel, Receipt.class);
        receipt.setaPackage(null);

        receiptRepository.save(receipt);
        receipt.setaPackage(packageRepository.findById(receiptServiceModel.getaPackage().getId()));
        receipt.setRecipient(userRepository.findByUsername(receiptServiceModel.getRecipient()));

        receiptRepository.merge(receipt);
    }
}
