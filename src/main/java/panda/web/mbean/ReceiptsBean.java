package panda.web.mbean;

import org.modelmapper.ModelMapper;
import panda.domain.model.view.ReceiptViewModel;
import panda.service.ReceiptService;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpSession;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Named(value = "receiptsBean")
@RequestScoped
public class ReceiptsBean {

    private List<ReceiptViewModel> packages;

    private ReceiptService receiptService;
    private ModelMapper modelMapper;

    public ReceiptsBean() {
    }

    @Inject
    public ReceiptsBean(ModelMapper modelMapper,
                        ReceiptService receiptService) {
        this.modelMapper = modelMapper;
        this.receiptService = receiptService;
    }

    @PostConstruct
    private void init() {
        HttpSession session = (HttpSession) FacesContext
                .getCurrentInstance()
                .getExternalContext()
                .getSession(true);

        this.packages = this.receiptService
                .findAllReceiptsByUsername((String) session.getAttribute("username"))
                .stream()
                .map(r -> {
                    ReceiptViewModel receipt = this.modelMapper.map(r, ReceiptViewModel.class);
                    receipt.setRecipient(r.getRecipient());
                    receipt.setRecipient(r.getaPackage().getDescription());
                    receipt.setRecipient(r.getIssuedOn().format(DateTimeFormatter.ofPattern("dd-MM-yyyy")));

                    return receipt;
                }).collect(Collectors.toList());
    }

    public List<ReceiptViewModel> getReceipts() {
        return packages;
    }

    public void setReceipts(List<ReceiptViewModel> packages) {
        this.packages = packages;
    }
}
