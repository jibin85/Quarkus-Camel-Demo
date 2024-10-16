package org.transformation.model.request;

public class Insurance {
    private CustomerDetails customerDetails;

    public CustomerDetails getCustomerDetails() {
        return customerDetails;
    }

    public void setCustomerDetails(CustomerDetails customerDetails) {
        this.customerDetails = customerDetails;
    }

    @Override
    public String toString() {
        return "Insurance{" +
                "customerDetails=" + customerDetails +
                '}';
    }
}
