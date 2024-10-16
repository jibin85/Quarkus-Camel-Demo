package org.transformation.model.request;

public class CustomerDetails {
    private Customer customer;
    private Premium premium;
    private Claim claim;

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Premium getPremium() {
        return premium;
    }

    public void setPremium(Premium premium) {
        this.premium = premium;
    }

    public Claim getClaim() {
        return claim;
    }

    public void setClaim(Claim claim) {
        this.claim = claim;
    }

    @Override
    public String toString() {
        return "CustomerDetails{" +
                "customer=" + customer +
                ", premium=" + premium +
                ", claim=" + claim +
                '}';
    }
}
