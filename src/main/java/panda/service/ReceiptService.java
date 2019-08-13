package panda.service;

import panda.domain.model.service.ReceiptServiceModel;

import java.util.List;

public interface ReceiptService {

    List<ReceiptServiceModel> findAllReceiptsByUsername(String username);

    ReceiptServiceModel findReceiptById(String id);

    void generateReceipt(ReceiptServiceModel receiptServiceModel);
}
