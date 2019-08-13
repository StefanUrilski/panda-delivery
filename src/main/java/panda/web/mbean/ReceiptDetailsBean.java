package panda.web.mbean;

import org.modelmapper.ModelMapper;
import panda.domain.model.service.ReceiptServiceModel;
import panda.domain.model.view.ReceiptDetailsViewModel;
import panda.domain.model.view.ReceiptViewModel;
import panda.service.ReceiptService;

import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import java.time.format.DateTimeFormatter;
import java.util.Map;

@Named(value = "receiptDetails")
public class ReceiptDetailsBean {

    private ReceiptDetailsViewModel receipt;

    private ModelMapper modelMapper;
    private ReceiptService receiptService;

    public ReceiptDetailsBean() {
    }

    @Inject
    public ReceiptDetailsBean(ModelMapper modelMapper,
                              ReceiptService receiptService) {
        this.modelMapper = modelMapper;
        this.receiptService = receiptService;
    }

    @PostConstruct
    private void init() {
        Map<String, String> requestParameters = FacesContext
                .getCurrentInstance()
                .getExternalContext()
                .getRequestParameterMap();

        String receiptId = requestParameters.get("id");

        ReceiptServiceModel rec = receiptService.findReceiptById(receiptId);

        receipt = modelMapper.map(rec, ReceiptDetailsViewModel.class);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");

        receipt.setIssuedOn(rec.getIssuedOn().format(formatter));
        receipt.setaPackage(rec.getaPackage().getDescription());
        receipt.setWeight(rec.getaPackage().getWeight());
        receipt.setShippingAddress(rec.getaPackage().getShippingAddress());
    }

    public ReceiptDetailsViewModel getReceipt() {
        return receipt;
    }

    public void setReceipt(ReceiptDetailsViewModel receipt) {
        this.receipt = receipt;
    }

}
