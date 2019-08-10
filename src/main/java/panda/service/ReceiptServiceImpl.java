package panda.service;

import org.modelmapper.ModelMapper;
import panda.repository.ReceiptRepository;

import javax.inject.Inject;

public class ReceiptServiceImpl implements ReceiptService {

    private final ModelMapper modelMapper;
    private final ReceiptRepository receiptRepository;

    @Inject
    public ReceiptServiceImpl(ModelMapper modelMapper,
                              ReceiptRepository receiptRepository) {
        this.modelMapper = modelMapper;
        this.receiptRepository = receiptRepository;
    }
}
