package panda.domain.entity;

import panda.domain.entity.enums.Status;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "packages")
public class Package extends BaseEntity {

    private String description;
    private Double weight;
    private String shippingAddress;
    private Status status;
    private LocalDateTime estimatedDeliveryDate;
    private User recipient;
    private Receipt receipt;

    @Column(name = "description", nullable = false)
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Column(name = "weight")
    public Double getWeight() {
        return weight;
    }

    public void setWeight(Double weight) {
        this.weight = weight;
    }

    @Column(name = "shipping_address", nullable = false)
    public String getShippingAddress() {
        return shippingAddress;
    }

    public void setShippingAddress(String shippingAddress) {
        this.shippingAddress = shippingAddress;
    }

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    @Column(name = "estimated_delivery_date")
    public LocalDateTime getEstimatedDeliveryDate() {
        return estimatedDeliveryDate;
    }

    public void setEstimatedDeliveryDate(LocalDateTime estimatedDeliveryDate) {
        this.estimatedDeliveryDate = estimatedDeliveryDate;
    }

    @ManyToOne(targetEntity = User.class)
    public User getRecipient() {
        return recipient;
    }

    public void setRecipient(User recipient) {
        this.recipient = recipient;
    }

    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    public Receipt getReceipt() {
        return receipt;
    }

    public void setReceipt(Receipt receipt) {
        this.receipt = receipt;
    }

}
