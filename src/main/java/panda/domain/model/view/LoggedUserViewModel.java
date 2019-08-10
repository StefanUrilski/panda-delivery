package panda.domain.model.view;

import panda.domain.entity.Package;
import panda.domain.entity.Receipt;

import java.util.List;

public class LoggedUserViewModel {

    private String username;
    private String role;
    private List<Package> packages;
    private List<Receipt> receipts;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public List<Package> getPackages() {
        return packages;
    }

    public void setPackages(List<Package> packages) {
        this.packages = packages;
    }

    public List<Receipt> getReceipts() {
        return receipts;
    }

    public void setReceipts(List<Receipt> receipts) {
        this.receipts = receipts;
    }
}
