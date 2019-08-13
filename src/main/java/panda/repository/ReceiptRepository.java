package panda.repository;

import panda.domain.entity.Receipt;

import java.util.List;

public interface ReceiptRepository extends GenericRepository<Receipt, String> {

    List<Receipt> findAllByUsername(String username);

    Receipt merge(Receipt receipt);
}
