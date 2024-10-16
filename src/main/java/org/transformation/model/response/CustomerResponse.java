package org.transformation.model.response;

public class CustomerResponse {
    private String customerName;
    private String customerId;
    private String customerAge;

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getCustomerAge() {
        return customerAge;
    }

    public void setCustomerAge(String customerAge) {
        this.customerAge = customerAge;
    }

    @Override
    public String toString() {
        return "CustomerResponse{" +
                "customerName='" + customerName + '\'' +
                ", customerId='" + customerId + '\'' +
                ", customerAge='" + customerAge + '\'' +
                '}';
    }
}
