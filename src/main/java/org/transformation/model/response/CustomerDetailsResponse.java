package org.transformation.model.response;

public class CustomerDetailsResponse {
    private String validClaim;
    private CustomerResponse customer;
    private ClaimResponse claim;

    public String getValidClaim() {
        return validClaim;
    }

    public void setValidClaim(String validClaim) {
        this.validClaim = validClaim;
    }

    public CustomerResponse getCustomer() {
        return customer;
    }

    public void setCustomer(CustomerResponse customer) {
        this.customer = customer;
    }

    public ClaimResponse getClaim() {
        return claim;
    }

    public void setClaim(ClaimResponse claim) {
        this.claim = claim;
    }

    @Override
    public String toString() {
        return "CustomerDetailsResponse{" +
                "validClaim='" + validClaim + '\'' +
                ", customer=" + customer +
                ", claim=" + claim +
                '}';
    }
}
